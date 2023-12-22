package com.cevdetkilickeser.yemapp.data.entity

import com.google.gson.annotations.SerializedName

class CartAnswer (@SerializedName("sepet_yemekler") var cart_foods: List<Carts>,
                  @SerializedName("success") var success:Int) {
}