package com.myoffice.ui.meetingdetails.view

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TextView
import android.app.DialogFragment
import android.app.Dialog
import android.widget.TimePicker
import com.myoffice.R
import com.myoffice.utils.AppConstants
import java.text.SimpleDateFormat
import java.util.*
/**
    Displays time picker for From and To time selection
 */
class TimePickerFragment: DialogFragment(), TimePickerDialog.OnTimeSetListener {
    private lateinit var calendar:Calendar
    private var isFrom:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isFrom = arguments.getBoolean(AppConstants.FROM)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return TimePickerDialog(
            activity, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth,this,hour,minute,false
        )
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        var activityInstance =  activity as MeetingDetailActivity
        var calendar: Calendar = Calendar.getInstance()
        calendar.set(0, 0, 0, hourOfDay, minute, 0)
        if(isFrom){
            val tv:TextView = activity.findViewById(R.id.fromHolder) as TextView
            activityInstance.fromCalenderTime = calendar
            setTimeDisplay(calendar,tv)
        }else{
            val tv:TextView = activity.findViewById(R.id.toHolder) as TextView
            activityInstance.toCalenderTime = calendar
            setTimeDisplay(calendar,tv)
        }
    }

    fun setTimeDisplay(calendar: Calendar,tv:TextView ){
        var dateConvert: Date = Date(calendar.timeInMillis)
        val sdf = SimpleDateFormat(AppConstants.TIME_FORMAT, Locale.US)
        var  formated_time = sdf.format(dateConvert);
        tv.text = "${formated_time}"
    }
}