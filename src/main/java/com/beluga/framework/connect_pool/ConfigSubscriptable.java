package com.beluga.framework.connect_pool;


import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.beluga.framework.exceptions.connectionpoolexceptions.ConnectionPoolException;

/*
 * This interface is used to subscribe to the configuration file changes.
 */
public interface ConfigSubscriptable {
    void reload() throws ConnectionPoolException, IOException, ParseException;
}
