package com.cevdetkilickeser.yemapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cevdetkilickeser.yemapp.data.entity.Carts
import com.cevdetkilickeser.yemapp.data.repo.FoodsDaoRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor (var foodsrepo:FoodsDaoRepository) : ViewModel() {
    private var auth = FirebaseAuth.getInstance()
    val user = auth.currentUser.toString()

    var cartList = MutableLiveData<List<Carts>>()

    init {
        getCartList(user)
        cartList = foodsrepo.getLDCartListRepo()
        Log.e("şş","cartVM init çalıştı")
    }

    fun getCartList(user:String){
        foodsrepo.getCartAllRepo(user)
        Log.e("şş","getCartListViewModel çalıştı")
    }

    fun deleteFromCart(food_id:Int, user: String){
        foodsrepo.deleteFromCartRepo(food_id, user)
        Log.e("şş","deleteFromCartViewModel çalıştı")

    }

    fun order(order: List<Carts>){
        foodsrepo.orderRepo(order)
    }
}