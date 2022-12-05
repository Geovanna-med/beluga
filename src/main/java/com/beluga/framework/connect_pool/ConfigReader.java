package com.beluga.framework.connect_pool;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConfigReader {
    private String pathFile = "/home/teo/Desktop/workspace/beluga/src/main/java/com/beluga/config/db.json";

    /* 
     * @param db JsonObject containing the data of a database
     * @return Object[] with the data of the databases
     */
    private Object[] parseDbObject(JSONObject db) {
        return new Object[] {
                db.get("name"),
                db.get("user"),
                db.get("password"),
                db.get("url"),
                ((Long) db.get("min_connections")).intValue(),
                ((Long) db.get("max_connections")).intValue(),
                ((Long) db.get("max_total_connections")).intValue()
        };
    }

    /*
     * @param dataBases: JsonArray of dataBases which will be converted to Object[][].
     * 
     * @return Object[][] with the dataBases information where eact Object[] contains
     * the information of each database defined in the configuration file.
     * 
     * @see src/main/java/com/beluga/config/db.json
     */
    private Object[][] fillDataBasesInfo(JSONArray dataBases) {
        Object[][] dataBasesInfo = new Object[dataBases.size()][];
        for (int i = 0; i < dataBases.size(); i++) {
            JSONObject db = (JSONObject) dataBases.get(i);
            dataBasesInfo[i] = parseDbObject(db);
        }
        return dataBasesInfo;
    }

    /*
     * @ return Object[][] with the following structure:
     *          { 
     *             {
     *              name, host, port, user, password, database, min_connections, max_connections, max_total_connections 
     *             }, 
     *             ...
     *          }
     */
    public Object[][] readDataBasesConfig() {
        JSONParser parser = new JSONParser();
        Object[][] dbs = new Object[][] {};

        try (FileReader reader = new FileReader(pathFile)) {
            Object obj = parser.parse(reader);
            JSONObject dbsJson = (JSONObject) obj;
            JSONArray dbsList = (JSONArray) dbsJson.get("dbs");

            dbs = fillDataBasesInfo(dbsList);

            return dbs;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return dbs;
    }
}
