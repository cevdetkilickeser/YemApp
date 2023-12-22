package com.cevdetkilickeser.yemapp.retrofit

import com.cevdetkilickeser.yemapp.data.entity.CRUDAnswer
import com.cevdetkilickeser.yemapp.data.entity.CartAnswer
import com.cevdetkilickeser.yemapp.data.entity.FoodsAnswer
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodsDao {
    @GET("yemekler/tumYemekleriGetir.php")
    fun allFoods() : Call<FoodsAnswer>

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    fun allCartFoods(@Field("kullanici_adi") user: String) : Call<CartAnswer>

    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    fun addToCart (@Field("yemek_adi") food_name:String,
                   @Field("yemek_resim_adi") food_pic:String,
                   @Field("yemek_fiyat") food_price:Int,
                   @Field("yemek_siparis_adet") quantity:Int,
                   @Field("kullanici_adi") user:String) : Call<CRUDAnswer>

    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    fun deleteFromCart (@Field("sepet_yemek_id") food_id:Int,
                        @Field("kullanici_adi") user:String) : Call<CRUDAnswer>

}