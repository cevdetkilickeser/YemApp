package com.cevdetkilickeser.yemapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cevdetkilickeser.yemapp.data.entity.Foods
import com.cevdetkilickeser.yemapp.data.repo.FoodsDaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor (foodsrepo: FoodsDaoRepository) : ViewModel() {

    var searchedFoodsLiveData = MutableLiveData<List<Foods>>()

    init {
        searchedFoodsLiveData = foodsrepo.getLDHomeListRepo()
        Log.e("şş", "searchviewmodel init çalıştı")
        for (i in searchedFoodsLiveData.value!!){
            Log.e("şş", i.food_name)
        }
    }

/*    fun searchFoods(searchQuery:String){
        foodsrepo.searchFoods(searchQuery)
    }

    fun observeSearchedFoods(){
        searchedFoodsLiveData = foodsrepo.getLDSearchListRepo()
    }*/

}