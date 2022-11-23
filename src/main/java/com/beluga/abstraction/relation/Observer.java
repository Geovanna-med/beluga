package com.beluga.abstraction.relation;

public interface Observer {

    //Should be overrided
    public void notifyChage();


    abstract void subscribeToObservable();
}
