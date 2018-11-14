package com.lovoo.utils

import android.content.Context
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner.Silent::class)
class AppUtilsVerification {

    @Mock
    private lateinit var mockContext: Context

    @Mock
    lateinit var appUtils: AppUtils

    @Mock
    lateinit var dateDifference: DateDifference

    @Test
    fun getFormatedDateCheck() {

        `when`(appUtils.getFormattedDate("")).thenReturn("")

        `when`(appUtils.getFormattedDate("2018-11-14")).thenReturn("14 11 2018")
    }

    @Test
    fun getDateDifferenceCheck(){
        var d1= Date()
        d1.time = -62167465799375

        var d2 = Date()
        d2.time = -62167462199683

        dateDifference.days=0
        dateDifference.minutes =59
        dateDifference.differenceInHours=0

        `when`(appUtils.getDateDifference(d1,d2)).thenReturn(dateDifference)
    }

}