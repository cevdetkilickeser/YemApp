package com.cevdetkilickeser.yemapp.data.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cevdetkilickeser.yemapp.data.entity.CRUDAnswer
import com.cevdetkilickeser.yemapp.data.entity.CartAnswer
import com.cevdetkilickeser.yemapp.data.entity.Carts
import com.cevdetkilickeser.yemapp.data.entity.Foods
import com.cevdetkilickeser.yemapp.data.entity.FoodsAnswer
import com.cevdetkilickeser.yemapp.retrofit.FoodsDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodsDaoRepository(var foodsdao: FoodsDao) {
    var quantityLastRepo:MutableLiveData<String>
    var totalAmountLastRepo:MutableLiveData<String>
    var homeListRepo:MutableLiveData<List<Foods>>
    var cartListRepo:MutableLiveData<List<Carts>>

    init {
        quantityLastRepo = MutableLiveData<String>("1")
        totalAmountLastRepo = MutableLiveData<String>("0 ₺")
        homeListRepo = MutableLiveData()
        cartListRepo = MutableLiveData()
    }

    fun getLDHomeListRepo() : MutableLiveData<List<Foods>>{
        return homeListRepo
    }

    fun getAllFoodsRepo(){
        foodsdao.allFoods().enqueue(object: Callback<FoodsAnswer> {
            override fun onResponse(call: Call<FoodsAnswer>?, response: Response<FoodsAnswer>) {
                if (response.body() != null){
                    val homeList = response.body()!!.foods
                    homeListRepo.value = homeList
                }
            }

            override fun onFailure(call: Call<FoodsAnswer>?, t: Throwable?) {}
        })
    }

    fun getLDCartListRepo() : MutableLiveData<List<Carts>> {
        return cartListRepo
    }

    fun getCartAllRepo(user:String){
        foodsdao.allCartFoods(user).enqueue(object: Callback<CartAnswer> {
            override fun onResponse(call: Call<CartAnswer>?, response: Response<CartAnswer>) {
                if (response.body() != null){
                    val cartList = response.body()!!.cart_foods
                    cartListRepo.value = cartList
                }else{
                    cartListRepo.value = emptyList()
                }
                Log.e("şş", "$user Repo" )
            }

            override fun onFailure(call: Call<CartAnswer>?, t: Throwable?) {}
        })
    }

    fun getQuantityRepo() : MutableLiveData<String> {
        return quantityLastRepo
    }

    fun getTotalAmountRepo() : MutableLiveData<String> {
        return totalAmountLastRepo
    }

    fun addToCartRepo(food: Foods, quantity: Int, user:String) {
        foodsdao.addToCart(food.food_name,food.food_pic,food.food_price,quantity,user).enqueue(object:
            Callback<CRUDAnswer> {
            override fun onResponse(call: Call<CRUDAnswer>?, response: Response<CRUDAnswer>) {

            }

            override fun onFailure(call: Call<CRUDAnswer>?, t: Throwable?) {}
        })
    }

    fun deleteFromCartRepo(food_id:Int, user:String){
        foodsdao.deleteFromCart(food_id,user).enqueue(object: Callback<CRUDAnswer> {
            override fun onResponse(call: Call<CRUDAnswer>?, response: Response<CRUDAnswer>) {

            }

            override fun onFailure(call: Call<CRUDAnswer>?, t: Throwable?) {}
        })
    }

    fun increaseQuantityRepo(food: Foods, q: Int) {
        var quantity = q

        if (quantity == 10) {}
        else {
            quantity++
            quantityLastRepo.value = quantity.toString()
            totalAmountLastRepo.value = ((food.food_price * quantity).toString() + " ₺")
        }
    }

    fun decreaseQuantityRepo(food: Foods, q: Int) {
        var quantity = q

        if (quantity == 1) {}
        else {
            quantity--
            quantityLastRepo.value = quantity.toString()
            totalAmountLastRepo.value = ((food.food_price * quantity).toString() + " ₺")
        }
    }

    fun orderRepo(order: List<Carts>){
        Log.e("şşş","${order.size} adet ürünün siparişi alındı.")
    }

    fun cleanDetailFragmentRepo(){
        quantityLastRepo = MutableLiveData<String>("0")
        totalAmountLastRepo = MutableLiveData<String>("0 ₺")
    }
}