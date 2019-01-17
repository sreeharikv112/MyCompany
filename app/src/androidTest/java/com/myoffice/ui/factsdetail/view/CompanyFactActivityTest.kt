package com.myoffice.ui.factsdetail.view

import android.content.Context
import android.content.Intent
import android.os.Parcel
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.myoffice.R
import com.myoffice.ui.factsdetail.model.CompanyFact
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.ArrayList

@RunWith(AndroidJUnit4::class)
class CompanyFactActivityTest(){

    var targetContext : Context = InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule
    val lvFactActivityRule: ActivityTestRule<CompanyFactActivity> =
        object : ActivityTestRule<CompanyFactActivity>(CompanyFactActivity::class.java, true, true) {
            override fun getActivityIntent(): Intent {

                val intent = Intent(targetContext, CompanyFactActivity::class.java)
                val list = ArrayList<String>()
                list.add("https://pixabay.com/get/eb32b20f28f7053ed1584d05fb1d4797e17fe5d01db50c4090f4c67fa4edbdbddd_640.jpg")
                list.add("https://pixabay.com/get/e837b50a2efd013ed1584d05fb1d4797e17fe5d01db50c4090f4c67fa4edbdbddd_640.jpg")
                val details = "Making our users happy"
                val companyFact = CompanyFact("HR", list, details)

                val parcel = Parcel.obtain()
                companyFact.writeToParcel(parcel, companyFact.describeContents())
                parcel.setDataPosition(0)
                val createdFromParcel = CompanyFact.createFromParcel(parcel)
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