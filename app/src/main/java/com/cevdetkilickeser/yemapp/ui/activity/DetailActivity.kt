package com.cevdetkilickeser.yemapp.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import com.cevdetkilickeser.yemapp.R
import com.cevdetkilickeser.yemapp.data.entity.Favs
import com.cevdetkilickeser.yemapp.data.entity.Foods
import com.cevdetkilickeser.yemapp.databinding.ActivityDetailBinding
import com.cevdetkilickeser.yemapp.ui.viewmodel.DetailViewModel
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@Suppress("DEPRECATION")
@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var user: String
    private lateinit var takenFood: Foods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail)
        Log.e("şş","Detail Activity çalıştı")

        takenFood = intent.getSerializableExtra("takenFood") as Foods
        binding.foodObject = takenFood

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser.toString()

        val tempViewModel: DetailViewModel by viewModels()
        viewModel = tempViewModel

        binding.detailActivity = this

        viewModel.increaseQuantity(takenFood,0)

        Picasso.get().load("http://kasimadalan.pe.hu/yemekler/resimler/${takenFood.food_pic}").resize(500, 500).into(binding.ivFoodPic)

        viewModel.favlist.observe(this){
            val fav = Favs(takenFood.food_id,takenFood.food_name,takenFood.food_price,takenFood.food_pic,user)
            it?.let {
                binding.checked = it.contains(fav)
            }
        }


        viewModel.quantityLast.observe(this){
            binding.quantity = it
        }

        viewModel.totalAmountLast.observe(this){
            binding.totalAmount = it
        }

        setContentView(binding.root)
    }

    fun checkBoxClick(){
        viewModel.checkBoxClick(takenFood,user)
    }

    fun buttonAddToCart (quantity:Int) {
        viewModel.addToCart(takenFood, quantity, user)
        viewModel.cleanDetailFragment()
    }

    fun buttonIncreaseQuantity(quantity:Int){
        viewModel.increaseQuantity(takenFood,quantity)
        if (viewModel.quantityLast.value!!.toInt() == 11) Toast.makeText(this,"Maksimum sipariş adedine ulaştınız.",
            Toast.LENGTH_SHORT).show()
    }

    fun buttonDecreaseQuantity(quantity:Int){
        viewModel.decreaseQuantity(takenFood,quantity)
        if (viewModel.quantityLast.value!!.toInt() == 1) Toast.makeText(this,"Minimum sipariş adedine ulaştınız.",
            Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cleanDetailFragment()
    }
}