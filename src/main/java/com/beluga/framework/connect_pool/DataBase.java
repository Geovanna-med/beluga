package com.beluga.framework.connect_pool;

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
    private String user;
    private String password;
    private String url;

    // connection pool config
    private int minConnection;
    private int maxConnection;
    private int maxTotalConnection;
    private PoolingDataSource dataSource = null;
    private GenericObjectPoolConfig<PoolableConnection> config = new GenericObjectPoolConfig<>();

    public DataBase(String name, String user, String password, String url) {
        this.name = name;
        this.user = user;
        this.password = password;
        this.url = url;
    }
    
    public void setMinConnection(int minConnection) {
        this.minConnection = minConnection;
        config.setMinIdle(this.minConnection);
    }

    public void setMaxConnection(int maxConnection) {
        this.maxConnection = maxConnection;
        config.setMaxIdle(this.maxConnection);
    }

    public void setMaxTotalConnection(int maxTotalConnection) {
        this.maxTotalConnection = maxTotalConnection;
        config.setMaxTotal(this.maxTotalConnection);
    }

    public Object getName() {
        return this.name;
    }

    /*
     * Wrapper method to get a connection from the connection pool.
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }


    public void updateConnectionPool() {
        // create connection pool
        // ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(null, config);

        // // create connection factory
        // ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
        //         "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.dbName + "?useSSL=false", this.user,
        //         this.password);

        // // create poolable connection factory
        // PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory,
        //         connectionPool, null);

        // // create pooling data source
        // dataSource = new PoolingDataSource<>(connectionPool);
    }

    /*
     * This method is used to set up the connection pool for the current database
     * @see https://commons.apache.org/proper/commons-dbcp/configuration.html
     */
    public void setUpConnectionPool() {
        Properties properties = new Properties();
        properties.setProperty("user", this.user);
        properties.setProperty("password", this.password);

        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
                this.url,
                properties);

        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);

        ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(
                poolableConnectionFactory, this.config);

        

        poolableConnectionFactory.setPool(connectionPool);

        this.dataSource = new PoolingDataSource<PoolableConnection>(connectionPool);
    }
}
