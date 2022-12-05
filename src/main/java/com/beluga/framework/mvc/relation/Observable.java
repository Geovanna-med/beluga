package com.beluga.framework.mvc.relation;

import java.util.ArrayList;

public abstract class Observable {
    protected ArrayList<Observer> observers = new ArrayList<>();


    protected void statusChanged(){
        this.notifyObservers();
        System.out.println("ChangeReceived");
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.notifyChage();
        }
    }

    public void subscribeObserver(Observer observer){
        this.observers.add(observer);
    }
}
