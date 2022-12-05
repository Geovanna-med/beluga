package com.beluga.framework.mvc;

public class Component {
    private Controller controller;

    public Component(Controller controller) {
        this.controller = controller;
    }

    public void start(){
        this.controller.startView();;
    }
    
}
