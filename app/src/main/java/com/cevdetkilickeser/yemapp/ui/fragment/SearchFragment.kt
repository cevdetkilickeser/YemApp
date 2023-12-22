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
    private lateinit var searchAdapter: SearchAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel:SearchViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search,container,false)

        binding.searchFragment = this

        viewModel.searchedFoodsLiveData.observe(viewLifecycleOwner){
            searchAdapter = SearchAdapter(this,requireContext(),it)
            binding.searchAdapter = searchAdapter

            var searchJob: Job? = null
            binding.etSearchbox.addTextChangedListener {
                searchJob?.cancel()
                searchJob = lifecycleScope.launch{
                    delay(500)
                    viewModel.searchFoods(it.toString())
                }
            }

            /*searchAdapter.setItems(it as ArrayList<Foods>)
            binding.searchAdapter = searchAdapter*/
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}