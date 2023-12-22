package com.cevdetkilickeser.yemapp.data.entity

import com.google.gson.annotations.SerializedName

data class FoodsAnswer(@SerializedName("yemekler") var foods: List<Foods>,
                       @SerializedName("success") var success:Int) {
}
