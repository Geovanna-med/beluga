package com.beluga.framework;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.beluga.framework.config.ConfigReader;
import com.beluga.framework.exceptions.mvcexceptions.NonExistentModelFunctionException;
import com.beluga.framework.mvc.Component;
import com.beluga.framework.mvc.ComponentMVCFactory;
import com.beluga.framework.mvc.ConfigReaderMVC;
import com.beluga.framework.transaction.Transaction;
import com.beluga.framework.transaction.TransactionFactory;
import com.beluga.framework.transaction.TransactionHandler;
import com.beluga.framework.transaction.TransactionPool;
import com.beluga.impl.urnaVirtual.UrnaVirtual;
import com.beluga.impl.urnaVirtual.UrnaVirtualController;
import com.beluga.impl.urnaVirtual.UrnaVirtualView;

/**
 * Hello world!
 *
 */
public class BelugApp {

    private TransactionPool transactionPool;
    private Component firstComponent;
    private String firstComponentName;
    private ConfigReaderMVC configReaderMVC;
    private HashMap<String, Component> components;

    public BelugApp() {
        this.transactionPool = new TransactionPool();
        this.components = new HashMap<>();
    }

    public void start() {
        this.loadConfig();
        this.createComponentsFromConfig();
        this.createTransactions();
        TransactionHandler.transactionPool = this.transactionPool;
        System.out.println(TransactionHandler.transactionPool);
        this.firstComponent.start();

    }

    public void loadConfig() {
        this.firstComponentName = "UrnaVirtual";
        configReaderMVC = new ConfigReaderMVC();
    }

    public void createComponentsFromConfig() {
        firstComponent = ComponentMVCFactory.createComponent((JSONObject) configReaderMVC.getComponentsArray().get(0));
        this.components.put("first", firstComponent);
    }

    private void createTransactions() {
        this.components.forEach((key, value) -> {
            JSONArray transactions = value.transactionsJSON;
            for (Object object : transactions) {
                JSONObject transactionJSON = (JSONObject) object;
                try {
                    Transaction transaction = TransactionFactory.createTransacation(
                            (String) transactionJSON.get("name"),
                            value.getController(), value.getModel(), (String) transactionJSON.get("modelFunction"));
                    this.transactionPool.insertTransaction(transaction);
                } catch (NonExistentModelFunctionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

    }

}
