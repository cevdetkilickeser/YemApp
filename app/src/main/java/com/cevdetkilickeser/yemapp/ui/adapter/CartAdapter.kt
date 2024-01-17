package com.cevdetkilickeser.yemapp.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cevdetkilickeser.yemapp.R
import com.cevdetkilickeser.yemapp.data.entity.Carts
import com.cevdetkilickeser.yemapp.databinding.CardCartBinding
import com.cevdetkilickeser.yemapp.ui.viewmodel.CartViewModel
import com.squareup.picasso.Picasso

class CartAdapter(var mContext: Context,
                  var cartList:List<Carts>,
                  var viewModel: CartViewModel,
                  var user: String
)
    : RecyclerView.Adapter<CartAdapter.CardCartHolder>(){

    inner class CardCartHolder(binding: CardCartBinding) : RecyclerView.ViewHolder(binding.root){
        var binding: CardCartBinding
        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardCartHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding:CardCartBinding = DataBindingUtil.inflate(layoutInflater, R.layout.card_cart,parent,false)
        return CardCartHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: CardCartHolder, position: Int) {
        val cart = cartList[position]
        val b = holder.binding
        b.cartObject = cart

        Picasso.get().load("http://kasimadalan.pe.hu/yemekler/resimler/${cart.food_pic}").into(b.ivFoodPicCard)

        b.ivDeleteFoodCard.setOnClickListener {
            viewModel.deleteFromCart(cart.cart_food_id)

            if (cartList.size != 1){
                viewModel.getCartList(user)
            }else{
                viewModel.cartList.value = emptyList()
            }
        }
    }
}