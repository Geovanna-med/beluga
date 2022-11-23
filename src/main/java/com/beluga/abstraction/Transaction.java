package com.beluga.abstraction;

import java.lang.reflect.Method;

public class Transaction {
    private String transactionName;
    private String control;
    private String model;
    private String function;

    public Transaction(String transactionName, String control, String model, String function) {
        this.control = control;
        this.model = model;
        this.function = function;
        this.transactionName = transactionName;
    }

    public void execute() throws Exception {
        Model model = ReflectionUtils.getModelInstance(this.model);
        
        Method method = Class.forName(this.model).getDeclaredMethod(this.function);

        method.invoke(Class.forName(this.model).cast(model));
    }


    public String getControl() {
        return control;
    }

    public String getModel() {
        return model;
    }

    public String getFunction() {
        return function;
    }

    public String getTransactionName() {
        return transactionName;
    }

}
