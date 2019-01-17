package com.myoffice.network

import android.text.TextUtils
import android.util.Log
import com.myoffice.ui.homelist.model.OfficeRoom
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Takes necessary parameters to make network requests
 * Invokes network service
 * Subscribes and observes call backs using Rx
 */
class NetworkProcessor(var networkService: NetworkService ) {


    fun getRemoteData(callback: NetworkResponseCallBack) {

        val singleResponse = networkService.getOfficeData()
        singleResponse.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ArrayList<OfficeRoom>> {
                override fun onSubscribe(d: Disposable) {}

                override fun onSuccess(response: ArrayList<OfficeRoom>) {
                    //Initiate success callback
                    callback.onSuccess(response)
                }

                override fun onError(e: Throwable) {
                    //Handles error response
                    Log.e(NetworkProcessor::class.java.canonicalName, "Error = " + e.toString())
                    val error = NetError()
                    if (!TextUtils.isEmpty(e.toString())) {
                        error.errorMsg = e.toString()
                    } else {
                        error.errorMsg = "Error in Processing Data"
                    }
                    callback.onError(error)
                }
            })
    }

    /**
     * Intimates network response
     */
    interface NetworkResponseCallBack {
        fun onSuccess(movieList: ArrayList<OfficeRoom>)

        fun onError(networkError: NetError)
    }
}