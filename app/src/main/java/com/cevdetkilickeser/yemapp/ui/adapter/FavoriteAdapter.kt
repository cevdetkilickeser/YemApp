package com.cevdetkilickeser.yemapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cevdetkilickeser.yemapp.data.entity.Favs
import com.cevdetkilickeser.yemapp.databinding.CardFavoriteBinding
import com.cevdetkilickeser.yemapp.ui.viewmodel.FavoriteViewModel
import com.squareup.picasso.Picasso

class FavoriteAdapter(var mContext: Context,
                      var likeList:List<Favs>,
                      var viewModel:FavoriteViewModel
)
    : RecyclerView.Adapter<FavoriteAdapter.CardFavoriteHolder>() {
    inner class CardFavoriteHolder (binding:CardFavoriteBinding) : RecyclerView.ViewHolder(binding.root){
        var binding: CardFavoriteBinding
        init {
            this.binding=binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardFavoriteHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = CardFavoriteBinding.inflate(layoutInflater,parent,false)
        return CardFavoriteHolder(binding)
    }

    override fun getItemCount(): Int {
        return likeList.size
    }

    override fun onBindViewHolder(holder: CardFavoriteHolder, position: Int) {
        val fav = likeList.get(position)
        val b = holder.binding
        b.favoriteObject = fav

        Picasso.get().load("http://kasimadalan.pe.hu/yemekler/resimler/${fav.food_pic}").resize(500, 500).into(b.imageViewFood)
        b.textViewFoodName.text = fav.food_name
        b.textViewFoodPrice.text = "${fav.food_price} ₺"

        b.cardViewLike.setOnClickListener {
            /*val action = LikeFragmentDirections.likeToDetail(food)
            Navigation.findNavController(it).navigate(action)*/
        }

    }
}