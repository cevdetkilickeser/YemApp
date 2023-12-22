package com.cevdetkilickeser.yemapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.cevdetkilickeser.yemapp.databinding.ActivityMainBinding
import com.cevdetkilickeser.yemapp.ui.activity.CartActivity
import com.cevdetkilickeser.yemapp.ui.activity.SigninActivity
import com.cevdetkilickeser.yemapp.ui.viewmodel.HomeViewModel
import com.cevdetkilickeser.yemapp.ui.viewmodel.SearchViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainActivity = this

        auth = FirebaseAuth.getInstance()

        binding.user = auth.currentUser!!.email.toString()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNav,navHostFragment.navController)
        NavigationUI.setupWithNavController(binding.drawerNav,navHostFragment.navController)

        val toggle = ActionBarDrawerToggle(this,binding.drawer,binding.toolbarMainActivity,0,0)
        toggle.drawerArrowDrawable.color = ContextCompat.getColor(this,R.color.white)
        binding.drawer.addDrawerListener(toggle)
        toggle.syncState()

    }

    override fun onBackPressed() {
        if (binding.drawer.isDrawerOpen(GravityCompat.START)){
            binding.drawer.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }

    fun signOutClick () {
        auth.signOut()
        val intent = Intent(this, SigninActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun cartClick(){
        val intent = Intent(this,CartActivity::class.java)
        startActivity(intent)
    }
}