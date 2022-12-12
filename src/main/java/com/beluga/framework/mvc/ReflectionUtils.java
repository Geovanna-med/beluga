package com.beluga.framework.mvc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReflectionUtils {

    private static Class<Model> getModelTypeClass(String className) throws ClassNotFoundException {
        Class<Model> ModelExtendedClass;
        ModelExtendedClass = (Class<Model>) Class.forName(className);
        return ModelExtendedClass;
    }

    private static Constructor<Model> getModelTypeConstructor(Class<Model> objClass)
            throws NoSuchMethodException, SecurityException {
        Constructor<Model> constructor;
        constructor = objClass.getConstructor();
        return constructor;
    }
    
    private static Model getModelTypeInstance(Constructor<Model> constructor)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object object;
        object = constructor.newInstance();
        return (Model) object;
    }

    public static Model getModelInstance(String className)
            throws Exception {
        Model Model;

        try {
            Class<Model> objClass = getModelTypeClass(className);
            Constructor<Model> objConstructor = getModelTypeConstructor(objClass);
            Model = getModelTypeInstance(objConstructor);
        } catch (ClassNotFoundException e) {
            throw e;
        } catch (NoSuchMethodException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }

        return Model;
    }

    private static Class<Controller> getControllerTypeClass(String className) throws ClassNotFoundException {
        Class<Controller> ControllerExtendedClass;
        ControllerExtendedClass = (Class<Controller>) Class.forName(className);
        return ControllerExtendedClass;
    }

    private static Constructor<Controller> getControllerTypeConstructor(Class<Controller> objClass)
            throws NoSuchMethodException, SecurityException {
        Constructor<Controller> constructor;
        constructor = objClass.getConstructor();
        return constructor;
    }
    
    private static Controller getControllerTypeInstance(Constructor<Controller> constructor)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object object;
        object = constructor.newInstance();
        return (Controller) object;
    }

    public static Controller getControllerInstance(String className)
            throws Exception {
        Controller Controller;

        try {
            Class<Controller> objClass = getControllerTypeClass(className);
            Constructor<Controller> objConstructor = getControllerTypeConstructor(objClass);
            Controller = getControllerTypeInstance(objConstructor);
        } catch (ClassNotFoundException e) {
            throw e;
        } catch (NoSuchMethodException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }

        return Controller;
    }

    private static Class<View> getViewTypeClass(String className) throws ClassNotFoundException {
        Class<View> ViewExtendedClass;
        ViewExtendedClass = (Class<View>) Class.forName(className);
        return ViewExtendedClass;
    }

    private static Constructor<View> getViewTypeConstructor(Class<View> objClass)
            throws NoSuchMethodException, SecurityException {
        Constructor<View> constructor;
        constructor = objClass.getConstructor(Model.class, Controller.class);
        return constructor;
    }
    
    private static View getViewTypeInstance(Constructor<View> constructor, Model model, Controller controller)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object object;
        object = constructor.newInstance(model, controller);
        return (View) object;
    }

    public static View getViewInstance(String className, Model model, Controller controller)
            throws Exception {
        View View;

        try {
            Class<View> objClass = getViewTypeClass(className);
            Constructor<View> objConstructor = getViewTypeConstructor(objClass);
            View = getViewTypeInstance(objConstructor, model, controller);
        } catch (ClassNotFoundException e) {
            throw e;
        } catch (NoSuchMethodException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }

        return View;
    }
    

}
