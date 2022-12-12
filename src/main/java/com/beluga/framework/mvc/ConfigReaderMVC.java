package com.beluga.framework.mvc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ConfigReaderMVC {

    private JSONArray components;

    public ConfigReaderMVC(){
        this.readConfigFile();
    }


    public void readConfigFile() {
        // JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("src/configMVC.json")) {
            
            JSONObject obj = (JSONObject) jsonParser.parse(reader);

            JSONArray mvcComponentsArray = (JSONArray) obj.get("components");
            components = mvcComponentsArray;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }

    public JSONArray getComponentsArray(){
        return this.components;
    }

}
