package com.beluga.framework.mvc;

import com.beluga.framework.mvc.relation.Observer;

public abstract class View implements Observer {

    protected Model model;
    protected Controller controller;

    protected View(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
        controller.view = this;
        this.subscribeToObservable();
    }

    public void subscribeToObservable() {
        this.model.subscribeObserver(this);
    }

    public abstract void show();
}
