package com.beluga.framework;

import java.util.HashMap;

import com.beluga.framework.exceptions.mvcexceptions.NonExistentModelFunctionException;
import com.beluga.framework.mvc.Component;
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
public class BelugApp 
{

    private TransactionPool transactionPool;
    private Component firstComponent;
    private String firstComponentName;


    public BelugApp(){
        this.transactionPool = new TransactionPool();
    }
    public static void main( String[] args )
    {
        
    }

    public void start(){
        this.loadConfig();
        try {
            this.fillTransactionPool();
        } catch (NonExistentModelFunctionException e) {
            e.printStackTrace();
        }
        TransactionHandler.transactionPool = this.transactionPool;
        System.out.println(TransactionHandler.transactionPool);
        this.firstComponent.start();

    }


    public void loadConfig(){
        this.firstComponentName = "UrnaVirtual";
    }

    private void fillTransactionPool() throws NonExistentModelFunctionException{

        UrnaVirtual urnaVirtual = new UrnaVirtual();
        UrnaVirtualController urnaVirtualController = new UrnaVirtualController();
        UrnaVirtualView urnaVirtualView = new UrnaVirtualView(urnaVirtual, urnaVirtualController);

        this.firstComponent = new Component(urnaVirtualController);
        //If confiig


        Transaction agregarVotoACandidato = TransactionFactory.createTransacation("añadirVoto", urnaVirtualController, urnaVirtual, "addVotoToCandidatoWithNombr");
        this.transactionPool.insertTransaction(agregarVotoACandidato);
    }
}
