package com.beluga.framework.connect_pool;

import java.io.Console;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        db.setUpConnectionPool();

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


    public static void main(String[] args) {
        DataBaseManager dbManager = DataBaseManager.getInstance();
        DataBase db = dbManager.getDataBase("db1");

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        System.out.println("Using db: " + db.getName());
        try {
            connection = db.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from movies");
            while (resultSet.next()) {
                System.out.println("title:" + resultSet.getString("title"));
                System.out.println("genre:" + resultSet.getString("genre"));
                System.out.println("director:" + resultSet.getString("director"));
            }

            if (resultSet != null ) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null)
                connection.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Done");


        DataBase db2 = dbManager.getDataBase("db2");
        
        System.out.println("Using db: " + db2.getName());
        try {
            connection = db2.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from series");
            while (resultSet.next()) {
                System.out.println("title:" + resultSet.getString("title"));
                System.out.println("caps:" + resultSet.getInt("caps"));
                System.out.println("genre:" + resultSet.getString("genre"));
            }

            if (resultSet != null)
                resultSet.close();
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Done");



    }


}
