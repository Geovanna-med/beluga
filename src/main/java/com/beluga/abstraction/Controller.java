package com.beluga.abstraction;

import java.awt.event.ActionListener;

import com.beluga.abstraction.relation.Observer;

public abstract class Controller implements Observer, ActionListener{
    protected Model model;
    protected View view;

    protected Controller(Model model){
        this.model = model;
        this.subscribeToObservable();
    }

    public void subscribeToObservable(){
        this.model.subscribeObserver(this);

    }

    public static void main(String[] args) {
        
    }


}
