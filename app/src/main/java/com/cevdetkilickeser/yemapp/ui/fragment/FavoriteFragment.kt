package com.cevdetkilickeser.yemapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.cevdetkilickeser.yemapp.R
import com.cevdetkilickeser.yemapp.databinding.FragmentFavoriteBinding
import com.cevdetkilickeser.yemapp.ui.adapter.FavoriteAdapter
import com.cevdetkilickeser.yemapp.ui.adapter.HomeAdapter
import com.cevdetkilickeser.yemapp.ui.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_favorite, container, false)

        viewModel.favlist.observe(viewLifecycleOwner){
            val favoriteAdapter = FavoriteAdapter(requireContext(),it,viewModel)
            binding.favoriteAdapter = favoriteAdapter
        }

        Log.e("şşş","onCreateView Çalıştı")

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val tempViewModel: FavoriteViewModel by viewModels()
        viewModel = tempViewModel

        Log.e("şşş","onCreate Çalıştı")
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavList()
    }
}