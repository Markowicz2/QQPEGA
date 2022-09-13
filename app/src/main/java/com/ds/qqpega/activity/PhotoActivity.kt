package com.ds.qqpega.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.PagerAdapter
import com.ds.qqpega.R
import com.ds.qqpega.adapter.SliderAdapterPhoto
import kotlinx.android.synthetic.main.activity_photo.*

class PhotoActivity : AppCompatActivity() {
    private var adapter: PagerAdapter = SliderAdapterPhoto(this, DetailsActivity.lista_photos)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        viewPagerActivity.adapter = adapter


      /*  setSupportActionBar(toolbar_photo)
        supportActionBar?.apply {
            title = DetailsActivity.place.name

            setDisplayHomeAsUpEnabled(true)
        }*/
       // supportActionBar?.setDisplayHomeAsUpEnabled(true)

        photo_title.text = DetailsActivity.place.name

        back_photo.setOnClickListener { finish() }


    }
}