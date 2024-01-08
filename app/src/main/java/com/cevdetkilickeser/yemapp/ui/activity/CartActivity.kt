package com.cevdetkilickeser.yemapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.cevdetkilickeser.yemapp.R
import com.cevdetkilickeser.yemapp.databinding.ActivityCartBinding
import com.cevdetkilickeser.yemapp.ui.adapter.CartAdapter
import com.cevdetkilickeser.yemapp.ui.viewmodel.CartViewModel
import com.cevdetkilickeser.yemapp.utils.User
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var viewModel: CartViewModel
    private lateinit var user: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_cart)

        user = User.user
        Log.e("şş",user)

        val tempViewModel: CartViewModel by viewModels()
        viewModel = tempViewModel

        binding.cartActivity = this

        viewModel.cartList.observe(this){
            val cartAdapter = CartAdapter(applicationContext,it,viewModel,user)
            binding.cartAdapter = cartAdapter

            var totalCartAmount = 0
            val cartList = viewModel.cartList.value
            if (cartList == null){
                binding.totalAmount = ""
            }else{
                for (i in cartList){
                    totalCartAmount += i.cart_food_quantity*i.food_price
                }
                binding.totalAmount = "$totalCartAmount ₺"
            }
        }

        setContentView(binding.root)
    }

    fun buttonOrder(){
        Log.e("şş","sipariş verildi")
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCartList(user)
    }
}