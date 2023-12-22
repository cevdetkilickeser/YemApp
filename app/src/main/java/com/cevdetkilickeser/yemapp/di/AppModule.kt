package com.cevdetkilickeser.yemapp.di

import android.content.Context
import androidx.room.Room
import com.cevdetkilickeser.yemapp.data.repo.FavsDaoRepository
import com.cevdetkilickeser.yemapp.data.repo.FoodsDaoRepository
import com.cevdetkilickeser.yemapp.retrofit.ApiUtils
import com.cevdetkilickeser.yemapp.retrofit.FoodsDao
import com.cevdetkilickeser.yemapp.room.MyDatabase
import com.cevdetkilickeser.yemapp.room.FavsDao
import com.cevdetkilickeser.yemapp.room.SearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideFoodsDaoRepository(foodsdao: FoodsDao,searchDao: SearchDao) : FoodsDaoRepository {
        return FoodsDaoRepository(foodsdao,searchDao)
    }

    @Provides
    @Singleton
    fun provideFoodsDao() : FoodsDao {
        return ApiUtils.getFoodsDao()
    }

    @Provides
    @Singleton
    fun providSearchDao(@ApplicationContext context: Context) : SearchDao {
        val db = MyDatabase.buildDatabase(context)
        return db.getSearchDao()
    }

    @Provides
    @Singleton
    fun provideFavsDaoRepository(favsdao: FavsDao) : FavsDaoRepository {
        return FavsDaoRepository(favsdao)
    }

    @Provides
    @Singleton
    fun provideFavsDao(@ApplicationContext context: Context) : FavsDao {
        val db = MyDatabase.buildDatabase(context)
        return db.getFavsDao()
    }
}
