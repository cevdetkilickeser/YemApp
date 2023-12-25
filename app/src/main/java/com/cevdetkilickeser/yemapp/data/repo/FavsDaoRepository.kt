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
    val user = FirebaseAuth.getInstance().currentUser.toString()

    var favListRepo: MutableLiveData<List<Favs>>

    init {
        favListRepo = MutableLiveData()
    }

    fun getLDFavListRepo() : MutableLiveData<List<Favs>> {
        return favListRepo
    }

    fun getAllFavsRepo(){
        val job = CoroutineScope(Dispatchers.Main).launch {
            favListRepo.value = favsdao.allFavs(user)
        }
    }

    fun addToFavs(fav: Favs){
        val job = CoroutineScope(Dispatchers.Main).launch {
            favsdao.addToFavs(fav)
            getAllFavsRepo()
        }
    }

    fun deleteFromFavs(fav:Favs){
        val job = CoroutineScope(Dispatchers.Main).launch {
            favsdao.deleteFromFavs(fav)
            getAllFavsRepo()
        }
    }
}