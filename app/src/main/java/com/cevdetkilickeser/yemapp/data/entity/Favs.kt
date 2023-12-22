package com.cevdetkilickeser.yemapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "favs")
data class Favs (@PrimaryKey
                 @ColumnInfo(name = "food_id") @NotNull var food_id:Int,
                 @ColumnInfo(name = "food_name") @NotNull var food_name:String,
                 @ColumnInfo(name = "food_price") @NotNull var food_price:Int,
                 @ColumnInfo(name = "food_pic") @NotNull var food_pic:String,
                 @ColumnInfo(name = "user") @NotNull var user:String) : Serializable {

}
