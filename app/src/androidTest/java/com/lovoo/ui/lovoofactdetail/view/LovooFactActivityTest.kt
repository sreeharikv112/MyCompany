package com.lovoo.ui.lovoofactdetail.view

import android.content.Context
import android.content.Intent
import android.os.Parcel
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.lovoo.R
import com.lovoo.ui.homelist.model.OfficeRoom
import com.lovoo.ui.lovoofactdetail.model.LovooFact
import com.lovoo.ui.meetingdetails.view.MeetingDetailActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.ArrayList

@RunWith(AndroidJUnit4::class)
class LovooFactActivityTest(){

    var targetContext : Context = InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule
    val lvFactActivityRule: ActivityTestRule<LovooFactActivity> =
        object : ActivityTestRule<LovooFactActivity>(LovooFactActivity::class.java, true, true) {
            override fun getActivityIntent(): Intent {

                val intent = Intent(targetContext, LovooFactActivity::class.java)
                val list = ArrayList<String>()
                list.add("https://firebasestorage.googleapis.com/v0/b/lv-trialwork.appspot.com/o/rooms%2FIMG_7.jpg?alt=media")
                list.add("https://firebasestorage.googleapis.com/v0/b/lv-trialwork.appspot.com/o/rooms%2FIMG_8.jpg?alt=media")
                val details = "Making our users happy"
                val lovooFact = LovooFact("HR", list, details)

                val parcel = Parcel.obtain()
                lovooFact.writeToParcel(parcel, lovooFact.describeContents())
                parcel.setDataPosition(0)
                val createdFromParcel = LovooFact.createFromParcel(parcel)
                intent.putExtra("LF", createdFromParcel)
                return intent
            }
        }

    @Test
    fun testUIElements(){

        onView(withText("Making our users happy")).check(matches(isDisplayed()))
        onView(withId(R.id.detailedText)).check(matches(withText("Making our users happy")))

        onView(withText("HR")).check(matches(isDisplayed()))
        onView(withId(R.id.title)).check(matches(withText("HR")))

    }

}