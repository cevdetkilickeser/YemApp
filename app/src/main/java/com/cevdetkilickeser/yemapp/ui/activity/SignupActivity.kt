package com.cevdetkilickeser.yemapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.cevdetkilickeser.yemapp.MainActivity
import com.cevdetkilickeser.yemapp.R
import com.cevdetkilickeser.yemapp.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_signup)
        binding.signupActivity = this
        auth = FirebaseAuth.getInstance()


    }

    fun buttonSignup (email:String,password:String,confirmPassword: String){
        if (checkSignupFields(email,password,confirmPassword)){
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful){
                        auth.signOut()
                        Toast.makeText(this,"Tebrikler, kayıt başarılı. Giriş yapabilirsiniz", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, SigninActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }.addOnFailureListener(this) { exception ->
                    Toast.makeText(this,exception.localizedMessage, Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun checkSignupFields(email:String, password: String, confirmPassword:String) : Boolean {
        binding.textInputLayoutEmail.error = null
        binding.textInputLayoutPassword.error = null
        binding.textInputLayoutConfirmPassword.error = null

        if (email == ""){
            binding.textInputLayoutEmail.error = "Bu alan zorunlu"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.textInputLayoutEmail.error = "E-Posta formatını kontrol edin"
            return false
        }
        if (password == ""){
            binding.textInputLayoutPassword.error = "Bu alan zorunlu"
            binding.textInputLayoutPassword.errorIconDrawable = null
            return false
        }
        if (confirmPassword == ""){
            binding.textInputLayoutConfirmPassword.error = "Bu alan zorunlu"
            binding.textInputLayoutConfirmPassword.errorIconDrawable = null
            return false
        }
        if (password != confirmPassword){
            binding.textInputLayoutConfirmPassword.error = "Şifreler eşleşmedi"
            return false
        }
        return true
    }

    fun textSignupClick(){
        val intent = Intent(this, SigninActivity::class.java)
        startActivity(intent)
        finish()
    }
}