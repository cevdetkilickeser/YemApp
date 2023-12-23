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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor (var foodsrepo: FoodsDaoRepository) : ViewModel() {

    var searchedFoodsLiveData = MutableLiveData<List<Foods>>()

    fun searchFoods(searchQuery:String){
        viewModelScope.launch {
            searchedFoodsLiveData = foodsrepo.searchFood(searchQuery)
        }
    }

    fun allFoodsRoom(){
        viewModelScope.launch {

        }
    }
}