package com.beluga.impl.urnaVirtual;

import javax.swing.JButton;

import com.beluga.framework.mvc.Controller;
import com.beluga.framework.mvc.Model;
import com.beluga.framework.transaction.TransactionHandler;


public class UrnaVirtualController extends Controller{


    public UrnaVirtualController() {
    }
    


    protected void addVoto(String candidato) {
        //This transaction shoudl be managed by the transaction handler
        this.data = candidato;
        TransactionHandler.getInstance().executeTransaction("addVoto");
        System.out.println("Called transaction to be executed: " + "addVoto");
        //((UrnaVirtual) model).addVotoToCandidatoWithNombre(candidato);
    }


    
    public void actionPerformed(java.awt.event.ActionEvent oe){
        String candidato  = ((JButton) oe.getSource()).getText();
        addVoto(candidato);
    }
}
