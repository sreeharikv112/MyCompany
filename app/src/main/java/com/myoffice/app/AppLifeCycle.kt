package com.myoffice.app

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * Handled App lifecycle
 */
class AppLifeCycle(var app:CompanyApplication) : LifecycleObserver {


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun gotStarted(){
        app.isAppAlive=true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun gotStopped(){
        app.isAppAlive=false
    }

}