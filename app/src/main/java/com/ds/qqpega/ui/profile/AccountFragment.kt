package com.ds.qqpega.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView

import com.ds.qqpega.MainActivity
import com.ds.qqpega.R
import com.ds.qqpega.activity.DetailsActivity
import com.ds.qqpega.activity.LoginActivity
import com.ds.qqpega.ui.places.PlacesFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : Fragment()  {


    lateinit var view1 :View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    fun preencherCampos(){
        var img_prof = view1.findViewById<CircleImageView>(R.id.img_profile)
        var fab = view1.findViewById<FloatingActionButton>(R.id.fab_img_profile)
        var nome_prof = view1.findViewById<TextView>(R.id.nome_profile)
        var email_prof = view1.findViewById<TextView>(R.id.email_profile)
        val fabOpen = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_open)
        nome_prof.text = MainActivity.usuario!!.name
        email_prof.text = MainActivity.usuario!!.email
        fab.startAnimation(fabOpen)
        if(MainActivity.usuario!!.profileUrl != ""){
            Picasso.get()
                .load(MainActivity.usuario!!.profileUrl)
                .into(img_prof)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 =inflater!!.inflate(R.layout.fragment_account, container, false)

        var logout = view1.findViewById<TextView>(R.id.logout_profile)
        logout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(it.context, LoginActivity::class.java)

            it.context.startActivity(intent)
            PlacesFragment.lista.clear()
        }

        preencherCampos()
        return view1
    }


}