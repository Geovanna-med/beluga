package com.beluga.framework.connect_pool;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.apache.log4j.Logger;

import org.json.simple.parser.ParseException;

import com.beluga.framework.exceptions.connectionpoolexceptions.ConnectionPoolException;

/*
 * This class is used to watch the configuration file changes. And
 * notify the subscribers. 
 */
public class ConfigWatcher extends Thread {
    private final String CONFIG_PATH;
    private final String CONFIG_FILE;
    private ConfigSubscriptable subscriptor;

    static Logger logger = Logger.getLogger(ConfigWatcher.class);

    public ConfigWatcher(ConfigSubscriptable subscriptor, String configPath, String configName) {
        this.CONFIG_PATH = configPath;
        this.CONFIG_FILE = configName;
        this.subscriptor = subscriptor;
    }

    @Override
    public void run() {
        try {
            this.watchConfig();
        } catch (InterruptedException | ConnectionPoolException | ParseException e) {
            logger.trace(e.getMessage());
        }
    }

    /*
     * @see watchConfig()
     */
    private void receiveFileEvent(WatchService watchService)
            throws ConnectionPoolException, IOException, ParseException, InterruptedException {
                
        while (true) {
            final WatchKey wk = watchService.take();
            for (WatchEvent<?> event : wk.pollEvents()) {

                final Path changed = (Path) event.context();

                logger.trace(changed);
                if (changed.endsWith(CONFIG_FILE)) {
                    this.subscriptor.reload();
                    logger.trace("Configuration file changed.");
                }

            }
            // reset the key
            boolean valid = wk.reset();
            if (!valid) {
                logger.trace("Key for configFile has been unregistered");
            }
        }
    }

    /*
     * Watch the configuration file changes.
     * 
     * @see com.beluga.framework.connect_pool.ConfigSubscriptable#reload()
     * 
     */
    private void watchConfig() throws InterruptedException, ConnectionPoolException, ParseException {
        Path path = Paths.get(CONFIG_PATH);

        try (final WatchService watchService = FileSystems.getDefault().newWatchService()) {
            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            receiveFileEvent(watchService);

        } catch (IOException e) {
            logger.trace(e.getMessage());
        }
    }
}
