package com.beluga.abstraction;

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
    

}
