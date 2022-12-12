package com.beluga.framework.mvc;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ComponentMVCFactory {

    public static Component createComponent(JSONObject componentJSON){
        Model model = null;
        Controller controller = null;
        try {
            model = ReflectionUtils.getModelInstance((String) componentJSON.get("model"));
            controller = ReflectionUtils.getControllerInstance((String) componentJSON.get("controller"));
            View view = ReflectionUtils.getViewInstance((String) componentJSON.get("view"), model, controller);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Component component = new Component(controller, model);
        component.transactionsJSON = (JSONArray) componentJSON.get("transactions");
        return component;
    }
    
}
