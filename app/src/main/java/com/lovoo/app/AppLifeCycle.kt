package com.lovoo.app

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * Handled App lifecycle
 */
class AppLifeCycle(var app:LovooApplication) : LifecycleObserver {


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun gotStarted(){
        app.isAppAlive=true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun gotStopped(){
        app.isAppAlive=false
    }

}