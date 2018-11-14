package com.lovoo.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Utility class for application components
 */
class AppUtils(var context:Context) {

    /**
     * Formats date input
     */
    fun getFormattedDate(inputDate: String): String {
        var date = ""
        try {
            var format = SimpleDateFormat(AppConstants.DATE_FORMAT_ONE)
            val newDate = format.parse(inputDate)

            format = SimpleDateFormat(AppConstants.DATE_FORMAT_TWO)
            date = format.format(newDate)
        } catch (e: ParseException) {
            Log.e(AppUtils::class.java!!.getSimpleName(), "Date ParseException = " + e.toString())
        }

        return date
    }

    /**
     * gets difference between dates
     */
    fun getDateDifference(startDate: Date, endDate: Date ): DateDifference {
        //milliseconds
        Log.d("TAG","startDate.time ${startDate.time}")
        Log.d("TAG","endDate.time ${endDate.time}")

        var different = endDate.time - startDate.time

        val diffInHours = TimeUnit.MILLISECONDS.toHours(different)

        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24

        val elapsedDays = different / daysInMilli
        different = different % daysInMilli

        val elapsedHours = different / hoursInMilli
        different = different % hoursInMilli

        val elapsedMinutes = different / minutesInMilli

        var dateDifference = DateDifference()
        dateDifference.days = elapsedDays
        dateDifference.hours = elapsedHours
        dateDifference.minutes = elapsedMinutes
        dateDifference.differenceInHours = diffInHours

        return dateDifference
    }

    /**
     * Checks for network
     */
    fun isNetworkConnected(): Boolean {
        var status = false
        val manager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting) {
            status = true
        }
        return status
    }
}