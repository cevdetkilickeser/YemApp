package com.cevdetkilickeser.yemapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cevdetkilickeser.yemapp.data.entity.Favs
import com.cevdetkilickeser.yemapp.data.entity.Foods
import com.cevdetkilickeser.yemapp.data.repo.FavsDaoRepository
import com.cevdetkilickeser.yemapp.data.repo.FoodsDaoRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
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
        favsrepo.getAllFavsRepo(user)
    }

    fun addToFavs(takenFood:Foods){
        favsrepo.addToFavs(takenFood,user)
    }

    fun deleteFromFavs(takenFood:Foods){
        favsrepo.deleteFromFavs(takenFood,user)
    }

    fun addToCart(food: Foods, quantity: Int, user: String) {
        foodsrepo.addToCartRepo(food,quantity,user)

    }

    fun increaseQuantity(food: Foods, q: Int) {
        foodsrepo.increaseQuantityRepo(food,q)
    }

    fun decreaseQuantity(food: Foods, q: Int) {
        foodsrepo.decreaseQuantityRepo(food,q)
    }

    fun cleanDetailFragment(){
        foodsrepo.cleanDetailFragmentRepo()
        quantityLast = foodsrepo.getQuantityRepo()
        totalAmountLast = foodsrepo.getTotalAmountRepo()
        Log.e("şş","cleanDetailFragmentViewModel çalıştı")
    }
}