package com.beluga.framework.transaction;

import javax.swing.text.StyledEditorKit.BoldAction;

public class TransactionHandler {
    private static TransactionHandler instance;
    public static TransactionPool transactionPool;

    private TransactionHandler() {

    }

    public void executeTransaction(String name) {
        transactionPool.getTransaction(name).execute();
    }

    public static synchronized TransactionHandler getInstance() {
        if (instance == null) {
            instance = new TransactionHandler();
        }
        return instance;
    }

}
