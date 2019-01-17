package com.myoffice.dipinject.components

import com.myoffice.ui.homelist.view.HomeListActivity
import com.myoffice.ui.meetingdetails.view.MeetingDetailActivity
import com.myoffice.ui.splash.SplashActivity
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