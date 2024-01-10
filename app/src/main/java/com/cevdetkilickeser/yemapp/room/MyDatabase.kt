package com.cevdetkilickeser.yemapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cevdetkilickeser.yemapp.data.entity.Favs
import com.cevdetkilickeser.yemapp.data.entity.Foods

@Database(entities = [Favs::class,Foods::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    companion object {

        @Volatile private var instance : MyDatabase? = null

        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        fun buildDatabase(context: Context): MyDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MyDatabase::class.java,
                "foodsdatabase")
                .build()
        }
    }

    abstract fun getFavsDao() : FavsDao

}