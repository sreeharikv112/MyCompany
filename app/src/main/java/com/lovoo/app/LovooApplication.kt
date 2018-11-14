package com.lovoo.app

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.lovoo.dipinject.components.ApplicationComponent
import com.lovoo.dipinject.components.DaggerApplicationComponent
import com.lovoo.dipinject.modules.ApplicationModule
import dagger.internal.DaggerCollections
import com.lovoo.dipinject.components.InjectionSubComponent
import com.lovoo.dipinject.modules.NetworkModule
import com.lovoo.utils.AppConstants
import java.io.File

/**
 * Application class
 * Initialize base components including Dagger components
 */
class LovooApplication : Application() {

    var isAppAlive: Boolean = false

    companion object {
        lateinit var mApplicationComponent : ApplicationComponent
        lateinit var instance: LovooApplication
        lateinit var mCacheFile : File
    }

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifeCycle(this))
        instance = this
        mCacheFile = File(cacheDir, AppConstants.OFFICE_DATA)
        initialiseDaggerComponent()
    }

    /**
     * Returns dagger component
     */
    fun getApplicationComponent():ApplicationComponent{
        if (mApplicationComponent == null) {
            initialiseDaggerComponent()
        }
        return mApplicationComponent
    }

    /**
     * initialise dagger component
     */
    fun initialiseDaggerComponent(){
        mApplicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .networkModule(NetworkModule(mCacheFile, this))
            .build()
    }

}