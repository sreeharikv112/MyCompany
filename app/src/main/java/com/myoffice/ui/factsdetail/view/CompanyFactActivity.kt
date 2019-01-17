package com.myoffice.ui.factsdetail.view

import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.myoffice.R
import com.myoffice.ui.factsdetail.model.CompanyFact
import com.myoffice.ui.base.BaseActivity
import com.myoffice.ui.factsdetail.adapter.ViewPagerAdapter
import com.myoffice.utils.AppConstants

import kotlinx.android.synthetic.main.activity_company_fact.*
import java.util.TimerTask
import java.util.Timer

/**
 * Displays details of @CompanyFact
 * Changes images in Viewpager in regular interval
 */
class CompanyFactActivity : BaseActivity() , CompanyFactView {

    private var currentPage:Int=0
    private lateinit var  lvFact: CompanyFact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lvFact = intent.extras.get(AppConstants.COMPANY_FACT) as CompanyFact
        renderView()
        init()
        setupData(lvFact)
    }

    override fun renderView() {
        setContentView(R.layout.activity_company_fact)
    }

    override fun init() {
        setSupportActionBar(toolbar)
        title = getString(R.string.office_fact)
    }

    override fun setupData(lvFact: CompanyFact) {

        var ol= intent.extras.getString(AppConstants.OFFICE_LEVEL)
        var rn = intent.extras.getString(AppConstants.ROOM_NUMBER)

        var title: TextView =  findViewById(R.id.title)
        var officeLevel: TextView =  findViewById(R.id.officeLevel)
        var roomNumber: TextView =  findViewById(R.id.roomNumber)
        var detailedText: TextView =  findViewById(R.id.detailedText)

        title.setText(lvFact.title)
        officeLevel.setText(ol)
        roomNumber.setText(rn)
        detailedText.setText(lvFact.text)

        var imageArray: ArrayList<String> = ArrayList()
        imageArray.addAll(lvFact.images)
        var viewPager: ViewPager = findViewById(R.id.viewpager)
        viewPager.adapter = ViewPagerAdapter(this, imageArray)
        val handler = Handler()
        val Update = Runnable {
            if (currentPage === imageArray.size) {
                currentPage = 0
            }
            viewPager.setCurrentItem(currentPage++, true)
        }
        val swipeTimer = Timer()

        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, 3000, 3000)
    }
}
