package com.myoffice.dipinject.components

import com.myoffice.dipinject.customscope.ApplicationScope
import com.myoffice.dipinject.modules.ApplicationModule
import com.myoffice.dipinject.modules.NetworkModule
import dagger.Component

/**
 * Dagger Application component
 * with @ApplicationModule and @NetworkModule
 */
@ApplicationScope
@Component(
    modules = arrayOf(ApplicationModule::class, NetworkModule::class)
)
interface ApplicationComponent {

    fun newInjectionComponent():InjectionSubComponent
}