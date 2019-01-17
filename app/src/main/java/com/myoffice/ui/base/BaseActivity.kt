package com.myoffice.ui.base

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.view.Gravity
import android.widget.Toast
import com.myoffice.app.CompanyApplication
import com.myoffice.dipinject.components.InjectionSubComponent

/**
 * Base Activity for all
 * Handles dependency injection initiation
 * Provides dependency to sub classes
 * Handles toast messages
 * Handles alert messages
 * Provides methods for UI handling
 */
open abstract class BaseActivity: AppCompatActivity() , AlertCallBack{

    private var mIsInjectionComponentUsed: Boolean=false
    private lateinit var mCallBackAlertDialog: AlertDialog

    fun getInjectionComponent(): InjectionSubComponent {
        if (mIsInjectionComponentUsed) {
            throw IllegalStateException("should not use Injection more than once.")
        }
        mIsInjectionComponentUsed = true
        return (CompanyApplication).instance.getApplicationComponent().newInjectionComponent()
    }

    fun showToast(msg: String) {
        runOnUiThread {
            val toast = Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }

    fun showAlert(message :String,positiveBtnText: Int, negativeBtnText:Int){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(message)
        dialogBuilder.setPositiveButton(positiveBtnText) { dialog, whichButton -> handlePositiveAlertCallBack() }
        dialogBuilder.setNegativeButton(negativeBtnText) { dialog, whichButton -> handleNegativeAlertCallBack() }
        mCallBackAlertDialog = dialogBuilder.create()
        mCallBackAlertDialog.setCancelable(false)
        mCallBackAlertDialog.show()
    }

    abstract fun renderView()

    abstract fun init()

    override fun handleNegativeAlertCallBack() {
        mCallBackAlertDialog.dismiss()
    }

    override fun handlePositiveAlertCallBack() {
        mCallBackAlertDialog.dismiss()
    }
}