package com.beluga.abstraction;

import com.beluga.abstraction.relation.Observer;

public abstract class View implements Observer {

    protected Model model;
    protected Controller controller;

    protected View(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
        this.subscribeToObservable();
    }

    public void subscribeToObservable() {
        this.model.subscribeObserver(this);

    }

}
