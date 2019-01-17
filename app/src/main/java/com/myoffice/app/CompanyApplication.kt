package com.myoffice.app

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.myoffice.dipinject.components.ApplicationComponent
import com.myoffice.dipinject.components.DaggerApplicationComponent
import com.myoffice.dipinject.modules.ApplicationModule
import com.myoffice.dipinject.modules.NetworkModule
import com.myoffice.utils.AppConstants
import java.io.File

/**
 * Application class
 * Initialize base components including Dagger components
 */
class CompanyApplication : Application() {

    var isAppAlive: Boolean = false

    companion object {
        lateinit var mApplicationComponent : ApplicationComponent
        lateinit var instance: CompanyApplication
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