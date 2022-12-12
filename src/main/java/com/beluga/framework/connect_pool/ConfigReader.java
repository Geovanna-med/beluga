package com.beluga.framework.connect_pool;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.beluga.framework.exceptions.connectionpoolexceptions.*;

public class ConfigReader {
    private String pathFile;

    public ConfigReader(String pathFile) {
        this.pathFile = pathFile;
    }

    /*
     * @param db JsonObject containing the data of a database
     * 
     * @return Object[] with the data of the databases
     */
    private Object[] parseDbObject(JSONObject db) throws IncorrectFormatOnConfigFileException {

        Object name = db.get("name");
        Object user = db.get("user");
        Object password = db.get("password");
        Object url = db.get("url");
        Object min_connections = ((Long) db.get("min_connections")).intValue();         
        Object max_connections = ((Long) db.get("max_connections")).intValue();
        Object max_total_connections = ((Long) db.get("max_total_connections")).intValue();


        if (name == null)
            throw new IncorrectFormatOnConfigFileException("The name of the database is not defined");
        if (user == null)
            throw new IncorrectFormatOnConfigFileException("The user of the database is not defined");
        if (password == null)
            throw new IncorrectFormatOnConfigFileException("The password of the database is not defined");
        if (url == null)
            throw new IncorrectFormatOnConfigFileException("The url of the database is not defined");
        if (min_connections == null)
            throw new IncorrectFormatOnConfigFileException("The min_connections of the database is not defined");
        if (max_connections == null)
            throw new IncorrectFormatOnConfigFileException("The max_connections of the database is not defined");
        if (max_total_connections == null)
            throw new IncorrectFormatOnConfigFileException("The max_total_connections of the database is not defined");

        return new Object[] {
            name, user, password, url, min_connections, max_connections, max_total_connections
        };
    }

    /*
     * @param dataBases: JsonArray of dataBases which will be converted to
     * Object[][].
     * 
     * @return Object[][] with the dataBases information where eact Object[]
     * contains
     * the information of each database defined in the configuration file.
     * 
     * @see src/main/java/com/beluga/config/db.json
     */
    private Object[][] fillDataBasesInfo(JSONArray dataBases) throws IncorrectFormatOnConfigFileException {
        Object[][] dataBasesInfo = new Object[dataBases.size()][];
        for (int i = 0; i < dataBases.size(); i++) {
            JSONObject db = (JSONObject) dataBases.get(i);
            dataBasesInfo[i] = parseDbObject(db);
        }
        return dataBasesInfo;
    }

    /*
     * @ return Object[][] with the following structure:
     * {
     * {
     * name, user, password url, min_connections, max_connections,
     * max_total_connections
     * },
     * ...
     * }
     */
    public Object[][] readDataBasesConfig() throws IncorrectFormatOnConfigFileException, NonExistentConfigFileException, IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object[][] dbs = new Object[][] {};

            FileReader reader;
            try {
                reader = new FileReader(pathFile);
                Object obj = parser.parse(reader);
                JSONObject dbsJson = (JSONObject) obj;
                JSONArray dbsList = (JSONArray) dbsJson.get("dbs");
    
                dbs = fillDataBasesInfo(dbsList);
    
                return dbs;
            } catch (FileNotFoundException e) {
                throw new NonExistentConfigFileException("The configuration file was not found");
            }
    }
}
