package com.ds.qqpega.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.ds.qqpega.R
import com.squareup.picasso.Picasso

class SliderAdapterPhoto: PagerAdapter {
    var context: Context
    var images: ArrayList<String>
    lateinit var inflater: LayoutInflater

    constructor(context: Context, images: ArrayList<String>) : super() {
        this.context = context
        this.images = images
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean =
        view == `object` as RelativeLayout


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var image: ImageView
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        var view: View = inflater.inflate(R.layout.img_adapter_photo, container, false)
        image = view.findViewById(R.id.slider_img_photo)

        if (!images[position].equals("")) {

            Picasso.get()
                .load(images[position])
                .into(image)
            // image.setBackgroundResource(images[position])
            container!!.addView(view)
        }

        return view

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container!!.removeView(`object` as RelativeLayout)
    }
}