package com.cevdetkilickeser.yemapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cevdetkilickeser.yemapp.data.entity.Foods

@Dao
interface SearchDao {
    @Query("SELECT * FROM search_foods WHERE food_name LIKE '%' || :searchQuery || '%'")
    suspend fun searchFood(searchQuery: String) : List<Foods>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFoodsRoom(vararg food: Foods)

    @Query("DELETE FROM search_foods")
    suspend fun deleteFoodsRoom()

    @Query("SELECT * FROM search_foods")
    suspend fun allFoodsRoom() : List<Foods>
}

