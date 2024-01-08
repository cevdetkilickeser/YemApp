package com.cevdetkilickeser.yemapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cevdetkilickeser.yemapp.data.entity.Carts
import com.cevdetkilickeser.yemapp.data.repo.FoodsDaoRepository
import com.cevdetkilickeser.yemapp.utils.User
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor (var foodsrepo:FoodsDaoRepository) : ViewModel() {
    val user = User.user

    var cartList = MutableLiveData<List<Carts>>()

    init {
        getCartList(user)
        cartList = foodsrepo.getLDCartListRepo()
    }

    fun getCartList(user:String){
        foodsrepo.getCartAllRepo(user)
    }

    fun deleteFromCart(food_id:Int){
        foodsrepo.deleteFromCartRepo(food_id, user)
    }

    fun order(order: List<Carts>){
        foodsrepo.orderRepo(order)
    }
}