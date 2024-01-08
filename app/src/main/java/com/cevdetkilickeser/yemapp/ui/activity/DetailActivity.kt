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
import com.cevdetkilickeser.yemapp.utils.User
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@Suppress("DEPRECATION")
@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var user: String
    private lateinit var takenFood: Foods
    private lateinit var fav: Favs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lateinitInitialize()

        Picasso.get().load("http://kasimadalan.pe.hu/yemekler/resimler/${takenFood.food_pic}").resize(500, 500).into(binding.ivFoodPic)

        viewModel.quantityLast.observe(this){
            binding.quantity = it
        }

        viewModel.totalAmountLast.observe(this){
            binding.totalAmount = it
        }

        if (viewModel.favList.value != null){
            for (food in viewModel.favList.value!!){
                if (food.food_id == takenFood.food_id){
                    binding.checked = true
                    break
                }else{
                    binding.checked = false
                }
            }
        }else{
            binding.checked = false
        }


        setContentView(binding.root)
    }

    fun checkBoxClick(){
        if (binding.checked != true){
            viewModel.addToFavs(fav)
            binding.checked = true
        }else{
            viewModel.deleteFromFavs(fav)
            binding.checked = false
        }
    }

    fun buttonAddToCart (quantity:Int) {
        viewModel.addToCart(takenFood, quantity)
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

    private fun lateinitInitialize(){
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail)

        binding.detailActivity = this

        val tempViewModel: DetailViewModel by viewModels()
        viewModel = tempViewModel

        user = User.user

        takenFood = intent.getSerializableExtra("takenFood") as Foods
        binding.foodObject = takenFood

        fav = Favs(0,takenFood.food_id,takenFood.food_name,takenFood.food_price,takenFood.food_pic,user)

        viewModel.increaseQuantity(takenFood,0)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cleanDetailFragment()
    }
}