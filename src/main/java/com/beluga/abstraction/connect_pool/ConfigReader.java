package com.beluga.abstraction.connect_pool;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConfigReader {
    private String pathFile = "com/beluga/config/db.json";

    private Object[] parseDbObject(JSONObject db) {
        return new Object[] {
                db.get("name"),
                db.get("host"),
                db.get("port"),
                db.get("user"),
                db.get("password"),
                db.get("database"),
                db.get("minConnection"),
                db.get("maxConnection"),
                db.get("maxTotalConnection")
        };
    }


    private Object[][] fillDataBasesInfo(JSONArray dataBases) {
        Object[][] dataBasesInfo = new Object[dataBases.size()][];
        for (int i = 0; i < dataBases.size(); i++) {
            JSONObject db = (JSONObject) dataBases.get(i);
            dataBasesInfo[i] = parseDbObject(db);
        }
        return dataBasesInfo;
    }

    public Object[][] readDataBasesConfig() {
        JSONParser parser = new JSONParser();
        Object[][] dbs = new Object[][]{};

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
