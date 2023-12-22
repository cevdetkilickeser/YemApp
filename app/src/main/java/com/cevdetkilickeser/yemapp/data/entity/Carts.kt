package com.cevdetkilickeser.yemapp.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Carts (@SerializedName("sepet_yemek_id") var cart_food_id:Int,
                  @SerializedName("yemek_adi") var food_name:String,
                  @SerializedName("yemek_fiyat") var food_price:Int,
                  @SerializedName("yemek_siparis_adet") var cart_food_quantity:Int,
                  @SerializedName("yemek_resim_adi") var food_pic:String,
                  @SerializedName("kullanici_adi") var user:String) : Serializable {
}