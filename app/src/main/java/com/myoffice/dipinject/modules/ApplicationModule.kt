package com.myoffice.dipinject.modules

import android.app.Application
import com.myoffice.dipinject.customscope.ApplicationScope
import com.myoffice.utils.AppUtils
import dagger.Module
import dagger.Provides

/**
 * Applicaiton module which can provide basic dependencies in app
 */
@Module
class ApplicationModule(var application: Application) {

    @Provides
    @ApplicationScope
    fun application(): Application {
        return  application
    }

    @Provides
    @ApplicationScope
    fun getUtils(): AppUtils{
        return AppUtils(application)
    }
}