package com.cevdetkilickeser.yemapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.cevdetkilickeser.yemapp.R
import com.cevdetkilickeser.yemapp.data.entity.Favs
import com.cevdetkilickeser.yemapp.data.entity.Foods
import com.cevdetkilickeser.yemapp.databinding.FragmentHomeBinding
import com.cevdetkilickeser.yemapp.ui.adapter.HomeAdapter
import com.cevdetkilickeser.yemapp.ui.viewmodel.HomeViewModel
import com.cevdetkilickeser.yemapp.ui.viewmodel.SearchViewModel
import com.cevdetkilickeser.yemapp.utils.User
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("DEPRECATION")
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var user: String
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        User.user = FirebaseAuth.getInstance().currentUser!!.email.toString()
        user = User.user

        Log.e("şş",user)

        val tempViewModel:HomeViewModel by viewModels()
        viewModel = tempViewModel

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)

        binding.homeFragment = this

        viewModel.homeList.observe(viewLifecycleOwner){
            homeAdapter = HomeAdapter( this,requireContext(),it,user)
            binding.homeAdapter = homeAdapter
        }
        Log.e("şş","onCreateView")
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Log.e("şş","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e("şş","onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("şş","onDestroy")
    }

    override fun onPause() {
        super.onPause()
        Log.e("şş","onPause")
    }
}
