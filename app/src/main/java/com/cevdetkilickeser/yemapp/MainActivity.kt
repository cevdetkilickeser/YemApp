package com.cevdetkilickeser.yemapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.cevdetkilickeser.yemapp.data.entity.UserInfo
import com.cevdetkilickeser.yemapp.databinding.ActivityMainBinding
import com.cevdetkilickeser.yemapp.databinding.DrawerHeaderBinding
import com.cevdetkilickeser.yemapp.ui.activity.CartActivity
import com.cevdetkilickeser.yemapp.ui.activity.SigninActivity
import com.cevdetkilickeser.yemapp.utils.User
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var user: String
    private lateinit var headerView: View
    private lateinit var headerBinding: DrawerHeaderBinding
    private lateinit var database: FirebaseFirestore
    private lateinit var switchTheme: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.mainActivity = this

        switchTheme = binding.switchTheme

        val sharedPreferences = getSharedPreferences("Mode", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val nightMode = sharedPreferences.getBoolean("night",false)

        // Day/Night Mode
        if (nightMode){
            switchTheme.isChecked = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        switchTheme.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("night",false)
                editor.apply()
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("night",true)
                editor.apply()
            }
        }
        // Day/Night Mode

        database = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        User.user = auth.currentUser!!.email.toString()
        user = User.user

        headerView = binding.drawerNav.getHeaderView(0)
        headerBinding = DrawerHeaderBinding.bind(headerView)

        downloadDrawerNavInfo()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNav,navHostFragment.navController)
        NavigationUI.setupWithNavController(binding.drawerNav,navHostFragment.navController)

        val toggle = ActionBarDrawerToggle(this,binding.drawer,binding.toolbarMainActivity,0,0)
        toggle.drawerArrowDrawable.color = ContextCompat.getColor(this,R.color.white)
        binding.drawer.addDrawerListener(toggle)
        toggle.syncState()

        setContentView(binding.root)
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

    fun downloadDrawerNavInfo() {
        database.collection("UserInfo").document(user).get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val userInfo = documentSnapshot.toObject(UserInfo::class.java)!!
                Picasso.get().load(userInfo.image).into(headerBinding.ivDrawerHeader)
                headerBinding.user = userInfo.name
            }else{
                headerBinding.ivDrawerHeader.setImageResource(R.drawable.profile)
                headerBinding.user = user
            }

        }.addOnFailureListener { exception ->
            Toast.makeText(this,exception.localizedMessage,Toast.LENGTH_SHORT).show()
        }
    }
}