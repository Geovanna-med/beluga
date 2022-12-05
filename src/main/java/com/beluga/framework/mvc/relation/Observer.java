package com.beluga.framework.mvc.relation;

public interface Observer {

    //Should be overrided
    public void notifyChage();


    abstract void subscribeToObservable();
}
