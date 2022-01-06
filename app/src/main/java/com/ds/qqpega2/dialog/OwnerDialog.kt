package com.ds.qqpega.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.viewpager.widget.PagerAdapter

import com.ds.qqpega.adapter.SliderAdapterPhoto
import com.ds.qqpega.R
import com.ds.qqpega.activity.DetailsActivity

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class OwnerDialog : DialogFragment() {

   var owner: String =""
    lateinit var bt_Send:Button
    lateinit var email_owner: TextView

    companion object{
        lateinit var context: Context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      /*  bt_send_owner.setOnClickListener{
            sendOwner()
        }*/
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var _view: View = requireActivity().getLayoutInflater().inflate(R.layout.dialog_owner, null)


        bt_Send = _view.findViewById(R.id.bt_send_owner)
        email_owner =_view.findViewById(R.id.edit_owner)

        bt_Send.setOnClickListener{
            sendOwner()
        }

        var alert = AlertDialog.Builder(activity)
        alert.setView(_view)





        return alert.create()
    }


    fun sendOwner() {
        Log.e("Dialog","OK")
        owner = email_owner.text.toString()
          FirebaseFirestore.getInstance().collection("Places")
              .document(DetailsActivity.place.name).update("owner",owner).addOnSuccessListener { dismiss() }

    }
}