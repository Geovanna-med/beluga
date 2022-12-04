package com.beluga.framework.transaction;

import java.util.HashMap;

public class TransactionPool {
    private HashMap<String, Transaction> transactions;

    public TransactionPool() {
        this.transactions = new HashMap<>();

    }


    public Transaction getTransaction(String name){
        System.out.println(name + " transaction being solicited.");
        return transactions.get(name);
    }

    public void insertTransaction(Transaction transaction){
        this.transactions.put(transaction.getName(), transaction);
        System.out.println("Added transaction: " + transaction.getName());
    }

}
