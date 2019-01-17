package com.myoffice.dipinject.customscope

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope

/**
 * Custom scope for global application singleton dependencies
 */

@MustBeDocumented
@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class ApplicationScope

