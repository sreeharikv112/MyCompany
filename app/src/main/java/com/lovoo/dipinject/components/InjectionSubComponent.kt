package com.lovoo.dipinject.components

import com.lovoo.ui.homelist.view.HomeListActivity
import com.lovoo.ui.meetingdetails.view.MeetingDetailActivity
import com.lovoo.ui.splash.SplashActivity
import dagger.Subcomponent

/**
 * Dagger sub component for injecting dependency in interested parties
 */
@Subcomponent
interface InjectionSubComponent
{
    fun inject(activity:SplashActivity)

    fun inject(activity: HomeListActivity)

    fun inject(activity: MeetingDetailActivity)

}