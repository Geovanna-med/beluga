package com.beluga.framework.transaction;

import java.lang.reflect.Method;

import com.beluga.framework.exceptions.mvcexceptions.NonExistentModelFunctionException;
import com.beluga.framework.mvc.Controller;
import com.beluga.framework.mvc.Model;

public class TransactionFactory {

    public static Transaction createTransacation(String name, Controller controller, Model model, String functionName) throws NonExistentModelFunctionException{
        System.out.println(name);
        Method transacationMethod = getModelMethod(model, functionName);
        return new Transaction(name, controller, model, transacationMethod);

    }


    private static Method getModelMethod(Model model, String functionName) throws NonExistentModelFunctionException{
        Method method = null;
        try {
            method = model.getClass().getMethod(functionName, Object.class);
        } catch (NoSuchMethodException e) {
            throw new NonExistentModelFunctionException();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return method;
    }
    
}
