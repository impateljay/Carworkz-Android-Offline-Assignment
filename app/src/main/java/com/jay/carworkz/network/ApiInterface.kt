package com.jay.carworkz.network

import com.jay.carworkz.model.AccessToken
import com.jay.carworkz.model.User
import com.jay.carworkz.utility.Constants
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ApiInterface {

    @Headers("Accept: application/json")
    @POST
    @FormUrlEncoded
    fun getAccessToken(@Url url:String, @Field("client_id") clientId: String, @Field("client_secret") clientSecret: String, @Field("code") code: String): Call<AccessToken>

    @Headers("Accept: application/json")
    @GET("users")
    fun getUsers(): Call<List<User>>

    companion object Factory {
        fun create(): ApiInterface {
            val okHttpClient = OkHttpClient.Builder()
                    .writeTimeout(10, TimeUnit.MINUTES)
                    .readTimeout(10, TimeUnit.MINUTES)
                    .build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}