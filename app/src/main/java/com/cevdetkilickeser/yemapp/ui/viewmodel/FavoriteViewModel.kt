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

@HiltViewModel

class FavoriteViewModel @Inject constructor (var favsrepo: FavsDaoRepository)  : ViewModel() {

    var favlist = MutableLiveData<List<Favs>>()
    private var user = FirebaseAuth.getInstance().currentUser.toString()

    init {
        getFavList()
        favlist = favsrepo.getLDFavListRepo()
    }

    fun getFavList(){
        viewModelScope.launch {
            favsrepo.getAllFavsRepo()
        }
    }

    fun deleteFromFav(fav: Favs){
        viewModelScope.launch {
            favsrepo.deleteFromFavs(fav)
            favlist = favsrepo.getLDFavListRepo()
        }
    }

    fun addToFavs(fav: Favs){
        viewModelScope.launch {
            favsrepo.addToFavs(fav)
            favlist = favsrepo.getLDFavListRepo()
        }
    }
}