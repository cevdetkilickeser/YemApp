package com.cevdetkilickeser.yemapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cevdetkilickeser.yemapp.data.entity.Favs
import com.cevdetkilickeser.yemapp.data.entity.Foods
import com.cevdetkilickeser.yemapp.data.repo.FavsDaoRepository
import com.cevdetkilickeser.yemapp.data.repo.FoodsDaoRepository
import com.cevdetkilickeser.yemapp.utils.User
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.security.auth.callback.Callback

@HiltViewModel

class DetailViewModel @Inject constructor (var foodsrepo:FoodsDaoRepository, var favsrepo: FavsDaoRepository) : ViewModel() {

    var user = User.user
    var quantityLast = MutableLiveData<String>()
    var totalAmountLast = MutableLiveData<String>()
    var favList = MutableLiveData<List<Favs>>()
    init {
        quantityLast = foodsrepo.getQuantityRepo()
        totalAmountLast = foodsrepo.getTotalAmountRepo()
        getFavList()
        favList = favsrepo.getLDFavListRepo()
    }

    fun getFavList(){
        viewModelScope.launch {
            favsrepo.getAllFavsRepo(user)
        }
    }

    fun addToFavs(fav:Favs){
        favsrepo.addToFavs(fav)
        favList = favsrepo.getLDFavListRepo()
    }

    fun deleteFromFavs(fav:Favs){
        favsrepo.deleteFromFavs(fav.user, fav.food_id)
        favList = favsrepo.getLDFavListRepo()
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