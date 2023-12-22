package com.cevdetkilickeser.yemapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cevdetkilickeser.yemapp.data.entity.Foods
import com.cevdetkilickeser.yemapp.databinding.CardHomeBinding
import com.cevdetkilickeser.yemapp.ui.fragment.HomeFragment
import com.cevdetkilickeser.yemapp.ui.fragment.SearchFragment
import com.squareup.picasso.Picasso

class SearchAdapter(var searchFragment: SearchFragment,
                    var mContext: Context,
                    var searchList: List<Foods>,
)
    : RecyclerView.Adapter<SearchAdapter.CardHomeHolder>() {


    class CardHomeHolder(val binding : CardHomeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHomeHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardHomeBinding.inflate(layoutInflater,parent,false)
        return CardHomeHolder(binding)
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: CardHomeHolder, position: Int) {
        val food = searchList[position]
        val b = holder.binding
        Picasso.get()
            .load("http://kasimadalan.pe.hu/yemekler/resimler/${food.food_pic}")
            .into(b.imageViewFood)

        b.textViewFoodName.text = food.food_name
        b.textViewFoodPrice.text = "${food.food_price} ₺"
    }
}