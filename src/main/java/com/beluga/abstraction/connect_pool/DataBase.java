package com.beluga.abstraction.connect_pool;

import java.sql.Connection;
import java.util.Properties;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class DataBase {

    // database connection config
    private String name;
    private String host;
    private String port;
    private String user;
    private String password;
    private String dbName;

    // connection pool config
    private int minConnection;
    private int maxConnection;
    private int maxTotalConnection;
    private PoolingDataSource dataSource = null;
    private GenericObjectPoolConfig<PoolableConnection> config = new GenericObjectPoolConfig<>();

    public DataBase(String name, String host, String port, String user, String password, String dbName) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.dbName = dbName;
    }
    
    public void setMinConnection(int minConnection) {
        this.minConnection = minConnection;
    }

    public void setMaxConnection(int maxConnection) {
        this.maxConnection = maxConnection;
    }

    public void setMaxTotalConnection(int maxTotalConnection) {
        this.maxTotalConnection = maxTotalConnection;
    }

    public Object getName() {
        return this.name;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void setUpConnectionPool() {
        Properties properties = new Properties();
        properties.setProperty("user", this.user);
        properties.setProperty("password", this.password);

        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
                "jdbc:mariadb://" + host + ":" + port + "/"
                        + dbName,
                properties
            );

        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);

        config.setMaxTotal(this.maxTotalConnection);
        config.setMaxIdle(this.maxConnection);
        config.setMinIdle(this.minConnection);

        ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnectionFactory, config);
        poolableConnectionFactory.setPool(connectionPool);

        this.dataSource = new PoolingDataSource<PoolableConnection>(connectionPool);
    }
}
