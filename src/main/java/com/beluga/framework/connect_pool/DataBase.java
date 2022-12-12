package com.beluga.framework.connect_pool;

import java.sql.Connection;
import java.util.Properties;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.lang3.Validate;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.beluga.framework.exceptions.connectionpoolexceptions.ConnectionPoolException;

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

    // connection pool
    private PoolingDataSource dataSource;
    private ConnectionFactory connectionFactory;
    private PoolableConnectionFactory poolableConnectionFactory;
    private ObjectPool<PoolableConnection> connectionPool;
    private GenericObjectPoolConfig<PoolableConnection> config = new GenericObjectPoolConfig<>();

    

    public DataBase(String name, String user, String password, String url, int minConnection, int maxConnection,
            int maxTotalConnection) throws ConnectionPoolException {
        this.name = name;
        this.user = user;
        this.password = password;
        this.url = url;
        validateConfigs(minConnection, maxConnection, maxTotalConnection);
    }
    

    private void validateConfigs(int min_connections, int max_connections, int max_total_connections) throws ConnectionPoolException {
        if (min_connections < 1 || min_connections > max_connections) {
            throw new ConnectionPoolException("minConnection must be greater than 0 and less-equal than maxConnection");
        }
        
        if (max_connections < 1 || max_connections > max_total_connections) {
            throw new ConnectionPoolException(
                    "maxConnection must be greater than 0 and less-equal than maxTotalConnection");
        }
        
        if (max_total_connections < 1) {
            throw new ConnectionPoolException("maxTotalConnection must be greater than 0 and greater-equal than minConnection");
        }
    }

    public void setMinConnection(int minConnection) throws ConnectionPoolException {
        if (minConnection == this.minConnection) {
            return;
        }
        
        if (minConnection < 1 || minConnection > this.maxConnection) {
            throw new ConnectionPoolException("minConnection must be greater than 0 and less-equal than maxConnection");
        }

        this.minConnection = minConnection;
        config.setMinIdle(this.minConnection);
    }

    public void setMaxConnection(int maxConnection) throws ConnectionPoolException {
        if (maxConnection == this.maxConnection) {
            return;
        }
        
        if (maxConnection < 1 || maxConnection >= this.maxTotalConnection) {
            throw new ConnectionPoolException("maxConnection must be greater than 0 and less-equal than maxTotalConnection");
        }

        this.maxConnection = maxConnection;
        config.setMaxIdle(this.maxConnection);
    }

    public void setMaxTotalConnection(int maxTotalConnection) throws ConnectionPoolException {
        if (maxTotalConnection == this.maxTotalConnection) {
            return;
        }
        
        if (maxTotalConnection < 1 || maxTotalConnection < this.minConnection) {
            throw new ConnectionPoolException("maxTotalConnection must be greater than 0 and greater-equal than minConnection");
        }

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
        try {
            // create connection pool
            connectionPool = new GenericObjectPool<>(poolableConnectionFactory, config);
            poolableConnectionFactory.setPool(connectionPool);
            dataSource = new PoolingDataSource<>(connectionPool);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * This method is used to set up the connection pool for the current database
     * @see https://commons.apache.org/proper/commons-dbcp/configuration.html
     */
    public void setUpConnectionPool() {
        Properties properties = new Properties();
        properties.setProperty("user", this.user);
        properties.setProperty("password", this.password);

        connectionFactory = new DriverManagerConnectionFactory(
                this.url,
                properties);

        poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);

        connectionPool = new GenericObjectPool<>(
                poolableConnectionFactory, this.config);

        poolableConnectionFactory.setPool(connectionPool);

        this.dataSource = new PoolingDataSource<PoolableConnection>(connectionPool);
    }
}
