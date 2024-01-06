package com.cevdetkilickeser.yemapp.data.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cevdetkilickeser.yemapp.data.entity.Favs
import com.cevdetkilickeser.yemapp.data.entity.Foods
import com.cevdetkilickeser.yemapp.room.FavsDao
import com.google.firebase.auth.FirebaseAuth
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

    fun getAllFavsRepo(user: String){
        val job = CoroutineScope(Dispatchers.Main).launch {
            favListRepo.value = favsdao.allFavs(user)
        }
    }

    fun addToFavs(fav: Favs){
        val job = CoroutineScope(Dispatchers.Main).launch {
            favsdao.addToFavs(fav)
            getAllFavsRepo(fav.user)
        }
    }

    fun deleteFromFavs(user: String, food_id: Int){
        val job = CoroutineScope(Dispatchers.Main).launch {
            favsdao.deleteFromFavs(user, food_id)
            getAllFavsRepo(user)
        }
    }
}