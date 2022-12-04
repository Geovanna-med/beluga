package com.beluga.framework.transaction;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public final class TransactionManager {

    

    /* 
    @SuppressWarnings("unchecked")
    public void readFile(String[] args) {
        // JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("transactions.json")) {
            Object obj = jsonParser.parse(reader);

            JSONArray transactionList = (JSONArray) obj;

            transactionList.forEach(tran -> parseTransaction((JSONObject) tran));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }
 */


}
