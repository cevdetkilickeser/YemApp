package com.cevdetkilickeser.yemapp.data.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cevdetkilickeser.yemapp.data.entity.Favs
import com.cevdetkilickeser.yemapp.data.entity.Foods
import com.cevdetkilickeser.yemapp.room.FavsDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavsDaoRepository(var favsdao: FavsDao) {
    var favListRepo: MutableLiveData<List<Favs>>

    init {
        favListRepo = MutableLiveData()
    }

    fun getLDFavListRepo() : MutableLiveData<List<Favs>> {
        return favListRepo
    }


    fun getAllFavsRepo(user:String){
        val job = CoroutineScope(Dispatchers.Main).launch {
            favListRepo.value = favsdao.allFavs(user)
        }
    }

    fun addToFavs(food: Foods, user:String){
        val job = CoroutineScope(Dispatchers.Main).launch {
            val liked = Favs(food.food_id,food.food_name,food.food_price,food.food_pic,user)
            favsdao.addToFavs(liked)
        }
    }

    fun deleteFromFavs(food:Foods,user:String){
        val job = CoroutineScope(Dispatchers.Main).launch {
            val disliked = Favs(food.food_id,food.food_name,food.food_price,food.food_pic,user)
            favsdao.deleteFromFavs(disliked)
        }
    }
}