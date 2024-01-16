package com.cevdetkilickeser.yemapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.cevdetkilickeser.yemapp.MainActivity
import com.cevdetkilickeser.yemapp.R
import com.cevdetkilickeser.yemapp.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth

class SigninActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signin)
        binding.signinActivity = this
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun buttonSignin(email:String,password:String){
        if (checkSigninFields(email,password)){
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful){
                        val currentUser = auth.currentUser!!.email.toString()
                        val toast = R.string.welcome
                        Toast.makeText(this,"${toast} ${currentUser}", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun checkSigninFields(email:String, password: String) : Boolean {
        binding.textInputLayoutEmail.error = null
        binding.textInputLayoutPassword.error = null

        if (email == ""){
            binding.textInputLayoutEmail.error = "E-Posta adresinizi girin"
            return false
        }
        if (password == ""){
            binding.textInputLayoutPassword.error = "Şifrenizi girin"
            binding.textInputLayoutPassword.errorIconDrawable = null
            return false
        }
        return true
    }

    fun txtCreateOneClick() {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
        finish()
    }
}