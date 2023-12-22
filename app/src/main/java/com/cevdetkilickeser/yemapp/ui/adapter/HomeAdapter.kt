package com.cevdetkilickeser.yemapp.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cevdetkilickeser.yemapp.R
import com.cevdetkilickeser.yemapp.data.entity.Foods
import com.cevdetkilickeser.yemapp.databinding.CardHomeBinding
import com.cevdetkilickeser.yemapp.ui.activity.DetailActivity
import com.cevdetkilickeser.yemapp.ui.fragment.HomeFragment
import com.squareup.picasso.Picasso

class HomeAdapter(var homeFragment: HomeFragment,
                  var mContext: Context,
                  var homeList: List<Foods>,
                  var user: String,
)
    : RecyclerView.Adapter<HomeAdapter.CardHomeHolder>(){
    inner class CardHomeHolder(binding: CardHomeBinding) : RecyclerView.ViewHolder(binding.root){
        var binding:CardHomeBinding
        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHomeHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding:CardHomeBinding = DataBindingUtil.inflate(layoutInflater, R.layout.card_home,parent,false)
        return CardHomeHolder(binding)
    }

    override fun getItemCount(): Int {
        return homeList.size
    }

    override fun onBindViewHolder(holder: CardHomeHolder, position: Int) {
        val food = homeList.get(position)
        val b = holder.binding
        b.foodObject = food

        Picasso.get()
            .load("http://kasimadalan.pe.hu/yemekler/resimler/${food.food_pic}")
            .into(b.imageViewFood)

        b.cardViewHome.setOnClickListener {
            val intent = Intent(mContext, DetailActivity::class.java)
            intent.putExtra("takenFood",food)
            mContext.startActivity(intent)
        }
    }
}