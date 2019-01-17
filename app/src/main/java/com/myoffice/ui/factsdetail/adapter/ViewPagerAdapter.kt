package com.myoffice.ui.factsdetail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.myoffice.R
import com.office.ui.base.GlideApp


/**
 * View Page Adapter for showing multiple images in CompanyFact
 * Uses @Glide
 */
class ViewPagerAdapter(var context: Context,var imagelist : ArrayList<String> )  : PagerAdapter() {

    private var layoutInflator: LayoutInflater = LayoutInflater.from(context)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageLayout = layoutInflator.inflate(R.layout.viewpager_item, container, false)!!
        val imageView = imageLayout
            .findViewById(R.id.image) as ImageView

        GlideApp.with(context).
            load(imagelist[position])
            .centerCrop()
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(imageView)
        container.addView(imageLayout,0)
        return imageLayout
    }
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view.equals(`object`)
    }

    override fun getCount(): Int {
        return imagelist.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}