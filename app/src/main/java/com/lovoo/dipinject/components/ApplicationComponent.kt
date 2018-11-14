package com.lovoo.dipinject.components

import com.lovoo.dipinject.customscope.ApplicationScope
import com.lovoo.dipinject.modules.ApplicationModule
import com.lovoo.dipinject.modules.NetworkModule
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