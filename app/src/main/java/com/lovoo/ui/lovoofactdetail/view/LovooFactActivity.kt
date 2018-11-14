package com.lovoo.ui.lovoofactdetail.view

import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.lovoo.R
import com.lovoo.ui.lovoofactdetail.model.LovooFact
import com.lovoo.ui.base.BaseActivity
import com.lovoo.ui.lovoofactdetail.adapter.ViewPagerAdapter
import com.lovoo.utils.AppConstants

import kotlinx.android.synthetic.main.activity_lovoo_fact.*
import java.util.TimerTask
import java.util.Timer

/**
 * Displays details of @LovooFact
 * Changes images in Viewpager in regular interval
 */
class LovooFactActivity : BaseActivity() , LovooFactView {

    private var currentPage:Int=0
    private lateinit var  lvFact: LovooFact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lvFact = intent.extras.get(AppConstants.LOVOO_FACT) as LovooFact
        renderView()
        init()
        setupData(lvFact)
    }

    override fun renderView() {
        setContentView(R.layout.activity_lovoo_fact)
    }

    override fun init() {
        setSupportActionBar(toolbar)
        title = getString(R.string.lovoo_fact)
    }

    override fun setupData(lvFact: LovooFact) {

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
