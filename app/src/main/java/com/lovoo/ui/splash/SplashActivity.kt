package com.lovoo.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View.VISIBLE
import androidx.core.widget.ContentLoadingProgressBar
import com.lovoo.R
import com.lovoo.ui.homelist.model.OfficeRoom
import com.lovoo.ui.base.BaseActivity
import com.lovoo.ui.homelist.view.HomeListActivity
import com.lovoo.utils.AppUtils
import javax.inject.Inject

/**
 * Splash activity
 */
class SplashActivity : BaseActivity()  {

    @Inject lateinit var mAppUtils : AppUtils
    private lateinit var mHandler : Handler
    private lateinit var mProgressBar : ContentLoadingProgressBar
    private var mDelay : Long = 1000
    private val TAG:String = SplashActivity::class.java.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        getInjectionComponent().inject(this)
        super.onCreate(savedInstanceState)
        renderView()
        init()
    }

    override fun renderView() {
        setContentView(R.layout.activity_splash)
    }

    override fun init() {
        mProgressBar = findViewById(R.id.waitingLoader)

        mHandler= Handler()
        mHandler.postDelayed({
            checkNetwork()
        },mDelay)
    }

    fun checkNetwork(){
        mProgressBar.visibility = VISIBLE
        if(mAppUtils.isNetworkConnected()) {
            pushNewActivity()
        }else{
            showAlert(getString(R.string.internet_error),R.string.retry,R.string.cancel_option)
        }
    }

    fun pushNewActivity(){
        var intent = Intent(this, HomeListActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun handleNegativeAlertCallBack() {
        finish()
    }

    override fun handlePositiveAlertCallBack() {
        checkNetwork()
    }
}
