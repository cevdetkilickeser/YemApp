package com.cevdetkilickeser.yemapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cevdetkilickeser.yemapp.data.entity.Favs
import com.cevdetkilickeser.yemapp.data.entity.Foods
import com.cevdetkilickeser.yemapp.data.repo.FavsDaoRepository
import com.cevdetkilickeser.yemapp.data.repo.FoodsDaoRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.security.auth.callback.Callback

@HiltViewModel

class DetailViewModel @Inject constructor (var foodsrepo:FoodsDaoRepository, var favsrepo: FavsDaoRepository) : ViewModel() {

    var quantityLast = MutableLiveData<String>()
    var totalAmountLast = MutableLiveData<String>()
    var favlist = MutableLiveData<List<Favs>>()
    private var user = FirebaseAuth.getInstance().currentUser.toString()

    init {
        quantityLast = foodsrepo.getQuantityRepo()
        totalAmountLast = foodsrepo.getTotalAmountRepo()
        getFavList()
        favlist = favsrepo.getLDFavListRepo()
    }

    fun getFavList(){
        viewModelScope.launch {
            favsrepo.getAllFavsRepo()
        }
    }

    fun checkBoxClick(takenFood: Foods,user: String){
        viewModelScope.launch {
            val fav = Favs(takenFood.food_id,takenFood.food_name,takenFood.food_price,takenFood.food_pic,user)
            if (favlist.value == null){
                favsrepo.addToFavs(fav)
                favlist = favsrepo.getLDFavListRepo()
            }else{
                if (favlist.value!!.contains(fav)){
                    favsrepo.deleteFromFavs(fav)
                    favlist = favsrepo.getLDFavListRepo()
                }
            }
        }
    }

    fun addToCart(food: Foods, quantity: Int, user: String) {
        viewModelScope.launch {
            foodsrepo.addToCartRepo(food,quantity,user)
        }
    }

    fun increaseQuantity(food: Foods, q: Int) {
        viewModelScope.launch {
            foodsrepo.increaseQuantityRepo(food,q)
        }
    }

    fun decreaseQuantity(food: Foods, q: Int) {
        viewModelScope.launch {
            foodsrepo.decreaseQuantityRepo(food,q)
        }
    }

    fun cleanDetailFragment(){
        viewModelScope.launch {
            foodsrepo.cleanDetailFragmentRepo()
            quantityLast = foodsrepo.getQuantityRepo()
            totalAmountLast = foodsrepo.getTotalAmountRepo()
        }
    }
}