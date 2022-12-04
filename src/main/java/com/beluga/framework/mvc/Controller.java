package com.beluga.framework.mvc;

import java.awt.event.ActionListener;

import com.beluga.framework.transaction.TransactionHandler;

public abstract class Controller implements ActionListener {
    protected View view;
    protected Object data;

    protected Controller() {
    }

    public void startView(){
        this.view.show();
    } 

    public Object extractData(){
        return this.data;
    }

    protected void executeTransaction(String name){
        TransactionHandler.getInstance().executeTransaction(name);
    }

}