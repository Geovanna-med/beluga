package com.beluga.abstraction;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TransactionManager {
    private ArrayList<Transaction> transactions = new ArrayList<Transaction>();

    private void parseTransaction(JSONObject transaction) {

        String transaction_name = (String) transaction.get("transaction_name");

        String control = (String) transaction.get("control");
        System.out.println(control);

        String model = (String) transaction.get("model");
        System.out.println(model);

        String function = (String) transaction.get("function");
        System.out.println(function);

        transactions.add(new Transaction(transaction_name, control, model, function));
    }

    
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



    public void executeTransaction(String transactionName) throws Exception {
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionName() == transactionName) {
                transaction.execute();
                break;
            }
        }
    }

}
