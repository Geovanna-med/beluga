package com.beluga.framework.connect_pool;

import java.io.File;
import java.util.EventListener;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.ConfigurationBuilderEvent;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.event.Event;
import org.apache.commons.configuration2.reloading.PeriodicReloadingTrigger;

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
public class DataBaseManager {
    private static DataBaseManager instance = null;
    private DataBase[] dataBases;
    private ConfigReader configReader;
    
    /*
     * When the manager is created, it will read the configuration file and
     * create the databases and therefore its connection pools. 
     */
    private DataBaseManager() {
        configReader = new ConfigReader();
        Object[][] dataBasesInfo = configReader.readDataBasesConfig();
        dataBases = new DataBase[dataBasesInfo.length];

        for (int i = 0; i < dataBasesInfo.length; i++) {
            dataBases[i] = creaDataBase(dataBasesInfo[i]);
        }
    }

    public static synchronized DataBaseManager getInstance() {
        if (instance == null) {
            instance = new DataBaseManager();
        }
        return instance;
    }

    /*
     * @param dataBaseInfo: Object[] with the following structure:
     *          { 
     *              name, host, port, user, password, database, min_connections, max_connections, max_total_connections 
     *          }
     * 
     * @return DataBase with the information provided in the dataBaseInfo parameter.
     */
    private DataBase creaDataBase(Object[] dataBaseInfo) {
        DataBase db = new DataBase(
                (String) dataBaseInfo[0],
                (String) dataBaseInfo[1],
                (String) dataBaseInfo[2],
                (String) dataBaseInfo[3]
        );

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

    public void watchConfig() {
        Parameters params = new Parameters();
        // Read data from this file
        File propertiesFile = new File("config.properties");

        ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration> configBuilder = new ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration>(
                PropertiesConfiguration.class)
                .configure(params.fileBased()
                        .setFile(propertiesFile));
        PeriodicReloadingTrigger reloadTrg = new PeriodicReloadingTrigger(configBuilder.getReloadingController(), null,
                5, TimeUnit.SECONDS);

            configBuilder.addEventListener(ConfigurationBuilderEvent.CONFIGURATION_REQUEST,
                new EventListener() {
                    @Override
                    public void onEvent(Event event) {
                            configBuilder.getReloadingController().checkForReloading(null);
                    }
                });
        reloadTrg.start();

    }

}
