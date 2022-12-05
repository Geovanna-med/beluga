package com.beluga.framework.connect_pool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


class Thread_UsingConnectionPool extends Thread {
    private DataBase db;
    private int id;

    public Thread_UsingConnectionPool(DataBase db, int id) {
        this.db = db;
        this.id = id;
    }

    @Override
    public void run() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = db.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM movies");
            while (rs.next()) {
                System.out.println("Thread " + id + ": " + rs.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

/*
 * Example usage of the connection pool when two threads
 * are requesting a connection at the same time from the
 * same connection-pool/database.
 */
public class PoolTest {
    public static void main(String[] args) {
        DataBaseManager dbManager = DataBaseManager.getInstance();
        DataBase db = dbManager.getDataBase("db1");

        db.setMaxTotalConnection(2);

        Thread thread1 = new Thread_UsingConnectionPool(db, 0);
        Thread thread2 = new Thread_UsingConnectionPool(db, 1);

        thread1.start();
        thread2.start();
    }
}
