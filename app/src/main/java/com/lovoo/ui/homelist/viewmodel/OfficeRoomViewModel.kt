package com.lovoo.ui.homelist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lovoo.network.NetError
import com.lovoo.network.NetworkProcessor
import com.lovoo.ui.homelist.view.HomeListActivity
import com.lovoo.ui.homelist.model.OfficeRoom
import com.lovoo.utils.AppUtils

import java.util.ArrayList

class OfficeRoomViewModel : ViewModel(), NetworkProcessor.NetworkResponseCallBack {

    private var mOfficeList: MutableLiveData<List<OfficeRoom>>? = null
    lateinit var mNetworkProcessor: NetworkProcessor
    lateinit var mAppUtils : AppUtils
    lateinit var homeListActivity: HomeListActivity

    internal var TAG = OfficeRoomViewModel::class.java.simpleName

    val officeData: LiveData<List<OfficeRoom>>
        get() {
            if (mOfficeList == null) {
                mOfficeList = MutableLiveData<List<OfficeRoom>>()
                loadData()
            }
            return mOfficeList as MutableLiveData<List<OfficeRoom>>
        }

    fun loadData() {
        if(mAppUtils.isNetworkConnected()) {
            mNetworkProcessor.getPopularList(this)
            homeListActivity.setRefreshingGone()
        }else{
            homeListActivity.showNetworkError()
        }
    }

    override fun onSuccess(movieList: ArrayList<OfficeRoom>) {
        mOfficeList!!.value = movieList
    }

    override fun onError(networkError: NetError) {
        homeListActivity.onError(networkError)
    }
}
