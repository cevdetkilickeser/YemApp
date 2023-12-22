package com.cevdetkilickeser.yemapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "search_foods")
data class Foods(@PrimaryKey
                 @ColumnInfo @SerializedName("yemek_id") var food_id:Int,
                 @ColumnInfo @SerializedName("yemek_adi") var food_name:String,
                 @ColumnInfo @SerializedName("yemek_fiyat") var food_price:Int,
                 @ColumnInfo @SerializedName("yemek_resim_adi") var food_pic:String) : Serializable

