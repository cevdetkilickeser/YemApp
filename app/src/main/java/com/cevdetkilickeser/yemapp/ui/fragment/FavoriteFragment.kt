package com.cevdetkilickeser.yemapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.cevdetkilickeser.yemapp.R
import com.cevdetkilickeser.yemapp.databinding.FragmentFavoriteBinding
import com.cevdetkilickeser.yemapp.ui.adapter.FavoriteAdapter
import com.cevdetkilickeser.yemapp.ui.viewmodel.FavoriteViewModel
import com.cevdetkilickeser.yemapp.utils.User
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var user: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_favorite, container, false)

        viewModel.favlist.observe(viewLifecycleOwner){
            favoriteAdapter = FavoriteAdapter(requireContext(),it,viewModel)
            binding.favoriteAdapter = favoriteAdapter
        }

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedFav = favoriteAdapter.likeList[position]
                viewModel.deleteFromFav(deletedFav.user, deletedFav.food_id)

                Snackbar.make(requireView(),"Deleted from favorites", Snackbar.LENGTH_LONG)
                    .setAction("UNDO",
                        View.OnClickListener {
                            viewModel.addToFavs(deletedFav)
                        }
                    ).show()
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvLike)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = User.user

        val tempViewModel: FavoriteViewModel by viewModels()
        viewModel = tempViewModel

    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavList()
    }
}