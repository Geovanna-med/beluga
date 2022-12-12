package com.beluga.framework.connect_pool;

import java.io.IOException;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.json.simple.parser.ParseException;

import com.beluga.framework.exceptions.connectionpoolexceptions.ConnectionPoolException;

/*
 * This class is used to manage the creation of the DataBase's and its 
 * connection pools. The manager will store each database defined in 
 * the configuration file and will return the requested database by its name.
 * 
 * @see src/main/java/com/beluga/config/db.json
 * 
 * The Manager is a syncronized singleton since it should be thread safe, allowing
 * the developer to create a single instance of the manager and use it to get the
 * whole application.
 * 
 */
public class DataBaseManager implements ConfigSubscriptable {
    private static DataBaseManager instance = null;
    private DataBase[] dataBases;
    
    private ConfigReader configReader;
    private ConfigWatcher configWatcher;

    private final String CONFIG_PATH = "/home/teo/Desktop/workspace/beluga/src/main/java/com/beluga/config/";
    private final String CONFIG_FILE = "db.json";

    ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration> configBuilder;

    /*
     * When the manager is created, it will read the configuration file and
     * create the databases and therefore its connection pools.
     */
    private DataBaseManager() throws ConnectionPoolException, IOException, ParseException   {
        configReader = new ConfigReader(CONFIG_PATH + CONFIG_FILE);
        configWatcher = new ConfigWatcher(this, CONFIG_PATH, CONFIG_FILE);
        configWatcher.start();

        Object[][] dataBasesInfo = configReader.readDataBasesConfig();
        dataBases = new DataBase[dataBasesInfo.length];

        for (int i = 0; i < dataBasesInfo.length; i++) {
            dataBases[i] = createDataBase(dataBasesInfo[i]);
        }
    }

    public static synchronized DataBaseManager getInstance() throws ConnectionPoolException, IOException, ParseException {
        if (instance == null) {
            instance = new DataBaseManager();
        }
        return instance;
    }

    /*
     * @param dataBaseInfo: Object[] with the following structure:
     * {
     * name, host, port, user, password, database, min_connections, max_connections,
     * max_total_connections
     * }
     * 
     * @return DataBase with the information provided in the dataBaseInfo parameter.
     */
    private DataBase createDataBase(Object[] dataBaseInfo) throws ConnectionPoolException {
        DataBase db = new DataBase(
                (String) dataBaseInfo[0],
                (String) dataBaseInfo[1],
                (String) dataBaseInfo[2],
                (String) dataBaseInfo[3]);

        db.setMinConnection((int) dataBaseInfo[4]);
        db.setMaxConnection((int) dataBaseInfo[5]);
        db.setMaxTotalConnection((int) dataBaseInfo[6]);
        db.setUpConnectionPool();

        return db;
    }

    /*
     * @param name: name of the database to be returned
     * 
     * @return DataBase with the given name
     */
    public DataBase getDataBase(String name) {
        for (DataBase dataBase : dataBases) {
            if (dataBase.getName().equals(name)) {
                return dataBase;
            }
        }
        return null;
    }


    /*
     * @see com.beluga.framework.connect_pool.ConfigSubscriptable#reload()
     */
    @Override
    public void reload() throws ConnectionPoolException, IOException, ParseException {
        Object[][] newDataBasesInfo = configReader.readDataBasesConfig();

        for (int i = 0; i < dataBases.length; i++) {
            dataBases[i].setMinConnection((int) newDataBasesInfo[i][4]);
            dataBases[i].setMaxConnection((int) newDataBasesInfo[i][5]);
            dataBases[i].setMaxTotalConnection((int) newDataBasesInfo[i][6]);

            dataBases[i].updateConnectionPool();
        }
    }
}
