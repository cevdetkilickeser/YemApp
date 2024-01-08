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
class SearchViewModel @Inject constructor (var foodsrepo: FoodsDaoRepository) : ViewModel() {

    var searchedFoodsLiveData = MutableLiveData<List<Foods>>()

    init {
        viewModelScope.launch {
            Log.e("şş", "searchviewmodel init çalıştı")

            // loadSearchList işlemini başlat
            val searchListJob = launch { loadSearchList() }

            // loadSearchList işlemi tamamlanana kadar bekle
            searchListJob.join()

            // observeSearchedFoods işlemini başlat
            observeSearchedFoods()

            // observeSearchedFoods işlemi tamamlanana kadar bekle
            searchedFoodsLiveData.value?.let {
                for (i in it) {
                    Log.e("şş", "${i.food_name} - ${i.food_price} - ${i.food_pic}")
                }
            }
        }
    }

    fun searchFoods(searchQuery:String){
        foodsrepo.searchFoods(searchQuery)
    }

    fun observeSearchedFoods(){
        searchedFoodsLiveData = foodsrepo.getLDSearchListRepo()
    }

    fun loadSearchList(){
        viewModelScope.launch {
            foodsrepo.getSearchFoodRoom()
            searchedFoodsLiveData = foodsrepo.getLDSearchListRepo()
            for (i in searchedFoodsLiveData.value!!){
                Log.e("şş",i.food_name)
            }
        }
    }
}