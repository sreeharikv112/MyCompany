package com.lovoo.ui.meetingdetails.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Factory class for accepting extra param
 */
class RoomListModelViewFactory : ViewModelProvider.Factory {

    var application: Application
    var param:String
    constructor(_application: Application,_param:String){
        application = _application
        param = _param
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RoomListViewModel(application, param) as T
    }
}