package com.cevdetkilickeser.yemapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cevdetkilickeser.yemapp.data.entity.Favs

@Dao
interface FavsDao {
    @Query("SELECT * FROM favs WHERE user = :user")
    suspend fun allFavs(user: String) : List<Favs>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavs(favs: Favs)

    @Query("DELETE FROM favs WHERE user = :user AND food_id = :food_id")
    suspend fun deleteFromFavs(user: String, food_id: Int)

}