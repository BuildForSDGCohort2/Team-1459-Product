package com.punzila.punzilateacherparent.ui.activity

import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.punzila.punzilateacherparent.R
import com.punzila.punzilateacherparent.databinding.DrawerHeaderLayoutBinding
import com.punzila.punzilateacherparent.utils.Prefs
import kotlinx.android.synthetic.main.host_activity.*


class HostActivity : AppCompatActivity() {
    lateinit var navController: NavController
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navViewBinding: DrawerHeaderLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        setStatusBarWhite(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_activity)
        val toolbar = customToolbar
        setSupportActionBar(toolbar)

        drawerLayout = drawer_layout
        navViewBinding = DrawerHeaderLayoutBinding.inflate(layoutInflater, navView, true)
        val navHost =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHost.navController

        val navInflater = navController.navInflater

        val graph = navInflater.inflate(R.navigation.main_graph)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.onBoarding ||
                destination.id == R.id.authFragment ||
                destination.id == R.id.loginFragment ||
                destination.id == R.id.signupFragment
            ) {
                toolbar.visibility = View.GONE
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            } else {
                toolbar.visibility = View.VISIBLE
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
        }
        if (!Prefs.getInstance(this)!!.hasCompleteWalkThrough!!) {
            if (mAuth.currentUser == null) {
             //   graph.startDestination = R.id.authFragment
                graph.startDestination = R.id.homeFragment
            } else {
              //  getUserData()
                graph.startDestination = R.id.homeFragment
            }
        } else {
            graph.startDestination = R.id.onBoarding

        }
        navController.graph = graph

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener {
            it.isChecked
            drawerLayout.closeDrawers()
            when (it.itemId) {
                R.id.action_logout -> {
                //    MyApplication.currentUser!!.active = false
//                    FirestoreUtil.updateUser(MyApplication.currentUser!!) {
//                        mAuth.signOut()
//                    }
                 //   googleSignInClient.signOut()
                   // MyApplication.currentUser = null
                  //  navController.navigate(R.id.action_logout)
                }
            }
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    fun setStatusBarWhite(activity: Activity) {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                val window = activity.window
                var flags = activity.window.decorView.systemUiVisibility
                flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                activity.window.decorView.systemUiVisibility = flags
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = ContextCompat.getColor(this, R.color.app_color_light_primary)
            }
            else -> {
              //  window.statusBarColor =  ContextCompat.getColor(this,R.color.app_colorPrimary)
            }
        }
    }
}