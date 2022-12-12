package com.beluga.framework.mvc;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Component {
    private Controller controller;
    private Model model;

    public JSONArray transactionsJSON;

    public Component(Controller controller, Model model) {
        this.controller = controller;
        this.model = model;
    }

    public void start(){
        this.controller.startView();
    }

    public static JSONArray getTransactionsOfJSONComponent(JSONObject component){
        return (JSONArray) component.get("transactions");
    }

    public Controller getController(){
        return this.controller;
    }

    public Model getModel(){
        return this.model;
    };
    
}
