package com.ds.qqpega

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.ds.qqpega.`object`.Places
import com.ds.qqpega.`object`.Users
import com.ds.qqpega.ui.places.PlacesFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.coroutines.newFixedThreadPoolContext

class MainActivity : AppCompatActivity() {

    companion object{
        var usuario: Users?? = null
    }


    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)




     /*   val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)






        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_map, R.id.nav_place, R.id.nav_event,R.id.nav_profile), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        var viewHeader = navView.getHeaderView(0)

        var title = viewHeader.findViewById<TextView>(R.id.nome_profile_menu)
        var email = viewHeader.findViewById<TextView>(R.id.email_menu)
        var img_menu = viewHeader.findViewById<ImageView>(R.id.img_profile_menu)
        title.text = usuario!!.name
        email.text = usuario!!.email
        if(usuario!!.profileUrl != "")
        Picasso.get()
            .load(usuario!!.profileUrl)
            .into(img_menu)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        PlacesFragment.lista.clear();
    }

    override fun onResume() {
        super.onResume()
        PlacesFragment.lista.clear();
    }

    private fun setcurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment,fragment)
            commit()
        }
    }
}