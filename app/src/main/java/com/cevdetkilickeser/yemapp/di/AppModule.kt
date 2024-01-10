package com.cevdetkilickeser.yemapp.di

import android.content.Context
import com.cevdetkilickeser.yemapp.data.repo.FavsDaoRepository
import com.cevdetkilickeser.yemapp.data.repo.FoodsDaoRepository
import com.cevdetkilickeser.yemapp.retrofit.ApiUtils
import com.cevdetkilickeser.yemapp.retrofit.FoodsDao
import com.cevdetkilickeser.yemapp.room.MyDatabase
import com.cevdetkilickeser.yemapp.room.FavsDao
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
    fun provideFoodsDaoRepository(foodsdao: FoodsDao) : FoodsDaoRepository {
        return FoodsDaoRepository(foodsdao)
    }

    @Provides
    @Singleton
    fun provideFoodsDao() : FoodsDao {
        return ApiUtils.getFoodsDao()
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
