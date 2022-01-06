package com.ds.qqpega.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.viewpager.widget.PagerAdapter
import com.ds.qqpega.R
import com.ds.qqpega.activity.DetailsActivity
import com.ds.qqpega.adapter.SliderAdapterPhoto
import kotlinx.android.synthetic.main.photo_dialog.*

class PhotoDialog : DialogFragment() {

    var image_view6: ImageView? = null

    companion object{
        lateinit var context: Context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private var adapter: PagerAdapter = SliderAdapterPhoto(PhotoDialog.context,DetailsActivity.lista_photos)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var _view: View = requireActivity().getLayoutInflater().inflate(R.layout.photo_dialog, null)

        viewPagerDialog.adapter = adapter
      //  this.image_view6 = _view.findViewById(R.id.imageView6)

        var alert = AlertDialog.Builder(activity)
        alert.setView(_view)

        /*this.button!!.setOnClickListener({

            Toast.makeText(activity, "Resultdo: ", Toast.LENGTH_LONG)
                .show() //mostra o resultado da soma

        })*/
      /*  Picasso.get()
            .load(DetailsActivity.lista_photos.get(0))
            .into(image_view6)*/

        return alert.create()
    }
}