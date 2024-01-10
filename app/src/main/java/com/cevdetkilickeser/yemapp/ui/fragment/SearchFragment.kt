package com.cevdetkilickeser.yemapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.cevdetkilickeser.yemapp.R
import com.cevdetkilickeser.yemapp.data.entity.Foods
import com.cevdetkilickeser.yemapp.databinding.FragmentSearchBinding
import com.cevdetkilickeser.yemapp.ui.adapter.HomeAdapter
import com.cevdetkilickeser.yemapp.ui.adapter.SearchAdapter
import com.cevdetkilickeser.yemapp.ui.viewmodel.SearchViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var searchList: List<Foods>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel:SearchViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search,container,false)

        binding.searchFragment = this

        searchList = viewModel.searchedFoodsLiveData.value!!

        binding.searchAdapter = SearchAdapter(requireContext(),searchList)

        binding.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                binding.searchAdapter?.filter?.filter(newText)
                return true
            }
        })
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        //viewModel.observeSearchedFoods()
    }

}