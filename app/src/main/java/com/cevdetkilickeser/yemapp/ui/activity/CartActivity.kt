package com.cevdetkilickeser.yemapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.cevdetkilickeser.yemapp.R
import com.cevdetkilickeser.yemapp.databinding.ActivityCartBinding
import com.cevdetkilickeser.yemapp.ui.adapter.CartAdapter
import com.cevdetkilickeser.yemapp.ui.viewmodel.CartViewModel
import com.cevdetkilickeser.yemapp.utils.User
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
        if (viewModel.cartList.value != null){
            val toast = R.string.ordered
            Toast.makeText(this,toast,Toast.LENGTH_SHORT).show()
            for (cart in viewModel.cartList.value!!){
                viewModel.deleteFromCart(cart.cart_food_id)
                viewModel.cartList.value = emptyList()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCartList(user)
    }
}