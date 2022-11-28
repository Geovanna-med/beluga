package com.beluga.abstraction.connect_pool;

public class DataBaseManager {

    private static DataBaseManager instance = null;
    private DataBase[] dataBases;
    private ConfigReader configReader;
    

    private DataBaseManager() {
        configReader = new ConfigReader();
        Object[][] dataBasesInfo = configReader.readDataBasesConfig();
        dataBases = new DataBase[dataBasesInfo.length];

        for (int i = 0; i < dataBasesInfo.length; i++) {
            dataBases[i] = creaDataBase(dataBasesInfo[i]);
        }

    }

    public static DataBaseManager getInstance() {
        if (instance == null) {
            instance = new DataBaseManager();
        }
        return instance;
    }


    private DataBase creaDataBase(Object[] dataBaseInfo) {
        DataBase db = new DataBase(
                (String) dataBaseInfo[0],
                (String) dataBaseInfo[1],
                (String) dataBaseInfo[2],
                (String) dataBaseInfo[3],
                (String) dataBaseInfo[4],
                (String) dataBaseInfo[5]
        );

        db.setMinConnection((int) dataBaseInfo[6]);
        db.setMaxConnection((int) dataBaseInfo[7]);
        db.setMaxTotalConnection((int) dataBaseInfo[8]);

        return db;
    }

    public DataBase getDataBase(String name) {
        for (DataBase dataBase : dataBases) {
            if (dataBase.getName().equals(name)) {
                return dataBase;
            }
        }
        return null;
    }



}
