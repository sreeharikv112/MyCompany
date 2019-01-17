package com.myoffice.ui.meetingdetails.view


import android.content.Context
import android.content.Intent
import android.os.Parcel

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.myoffice.ui.homelist.model.OfficeRoom
import com.myoffice.ui.factsdetail.model.CompanyFact
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import java.util.ArrayList

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.myoffice.R


@RunWith(AndroidJUnit4::class)
class MeetingActivityTest {

    var targetContext : Context = InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule
    val detailActivityRule: ActivityTestRule<MeetingDetailActivity> =
        object : ActivityTestRule<MeetingDetailActivity>(MeetingDetailActivity::class.java, true, true) {
            override fun getActivityIntent(): Intent {

                val intent = Intent(targetContext, MeetingDetailActivity::class.java)
                val list = ArrayList<String>()
                list.add("https://pixabay.com/get/eb32b20f28f7053ed1584d05fb1d4797e17fe5d01db50c4090f4c67fa4edbdbddd_640.jpg")
                list.add("https://pixabay.com/get/e837b50a2efd013ed1584d05fb1d4797e17fe5d01db50c4090f4c67fa4edbdbddd_640.jpg")
                val details = "Making our users happy is the main challenge for our team"
                val companyFact = CompanyFact("HMR", list, details)
                val officeRoom = OfficeRoom(
                    "Department One",
                    "Room 152",
                    "Department 01",
                    "Test Team",
                    6,
                    "idabc",
                    companyFact
                )
                val parcel = Parcel.obtain()
                officeRoom.writeToParcel(parcel, officeRoom.describeContents())
                parcel.setDataPosition(0)
                val createdFromParcel = OfficeRoom.createFromParcel(parcel)
                intent.putExtra("DATA", createdFromParcel)
                return intent
            }
        }

    @Test
    fun testUIElements(){
        onView(withText("Test Team")).check(matches(isDisplayed()))
        onView(withId(R.id.roomName)).check(matches(withText("Test Team")))
        onView(withText("Room 152")).check(matches(isDisplayed()))
        onView(withId(R.id.roomNumber)).check(matches(withText("Room 152")))
    }

    @Test
    fun testClickableUI(){
        onView(withId(R.id.btnBookRoom)).perform(click())
        onView(withId(R.id.bookFrom)).perform(click())
    }

}