package com.beluga.framework.transaction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.beluga.framework.mvc.Controller;
import com.beluga.framework.mvc.Model;

public class Transaction {
    private String name;
    private Controller controller;
    private Model model;
    private Method method;

    public Transaction(String name, Controller controller, Model model, Method method) {
        this.name = name;
        this.controller = controller;
        this.model = model;
        this.method = method;
    }

    protected String getName() {
        return name;
    }


    public void execute() {
        Object data = this.controller.extractData();
        try {
            this.method.invoke(this.model, data);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
