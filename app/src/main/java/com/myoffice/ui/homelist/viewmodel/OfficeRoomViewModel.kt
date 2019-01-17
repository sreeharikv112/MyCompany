package com.myoffice.ui.homelist.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myoffice.R
import com.myoffice.network.NetError
import com.myoffice.network.NetworkProcessor
import com.myoffice.ui.homelist.view.HomeListActivity
import com.myoffice.ui.homelist.model.OfficeRoom
import com.myoffice.utils.AppUtils
import com.myoffice.utils.JsonProcessor
import com.squareup.moshi.Moshi

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
            //For getting data from backend
            //mNetworkProcessor.getRemoteData(this)
            //To get local saved JSON
            try {
                var jsonProcessor = JsonProcessor()
                var stringJson = jsonProcessor.processAdsJSON(homeListActivity.resources, R.raw.response)
                val moshi = Moshi.Builder().build()
                val jsonAdapter = moshi.adapter<Array<OfficeRoom>>(Array<OfficeRoom>::class.java)
                var jsonResponse = jsonAdapter.fromJson(stringJson)?.toList()
                onSuccess(jsonResponse as ArrayList<OfficeRoom>)
                homeListActivity.setRefreshingGone()
            } catch (e: Exception) {
                Log.e(TAG,"Error in JSON conversion ==|| ${e} ||==")
            }
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
