package com.cevdetkilickeser.yemapp.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cevdetkilickeser.yemapp.R
import com.cevdetkilickeser.yemapp.data.entity.Foods
import com.cevdetkilickeser.yemapp.databinding.CardHomeBinding
import com.cevdetkilickeser.yemapp.ui.activity.DetailActivity
import com.cevdetkilickeser.yemapp.ui.fragment.HomeFragment
import com.cevdetkilickeser.yemapp.ui.fragment.SearchFragment
import com.squareup.picasso.Picasso

class SearchAdapter(var mContext: Context,
                    var searchList: List<Foods>
)
    : RecyclerView.Adapter<SearchAdapter.CardHomeHolder>(), Filterable {

    private var filteredList: List<Foods> = searchList

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()

                if (constraint.isNullOrEmpty()) {
                    filterResults.values = searchList
                } else {
                    val filteredItems = searchList.filter { item ->
                        item.food_name.contains(constraint, ignoreCase = true)
                    }
                    filterResults.values = filteredItems
                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as List<Foods>
                notifyDataSetChanged()
            }
        }
    }

    inner class CardHomeHolder(binding: CardHomeBinding) : RecyclerView.ViewHolder(binding.root){
        var binding: CardHomeBinding
        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHomeHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding: CardHomeBinding = DataBindingUtil.inflate(layoutInflater, R.layout.card_home,parent,false)
        return CardHomeHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: CardHomeHolder, position: Int) {
        val food = filteredList[position]
        val b = holder.binding
        b.foodObject = food

        Picasso.get()
            .load("http://kasimadalan.pe.hu/yemekler/resimler/${food.food_pic}")
            .into(b.imageViewFood)

        b.textViewFoodName.text = food.food_name
        b.textViewFoodPrice.text = "${food.food_price} ₺"

        b.cardViewHome.setOnClickListener {
            val intent = Intent(mContext, DetailActivity::class.java)
            intent.putExtra("takenFood",food)
            mContext.startActivity(intent)
        }
    }
}