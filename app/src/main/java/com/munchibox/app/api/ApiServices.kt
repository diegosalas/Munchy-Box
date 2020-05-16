
package com.munchybox.app.api




import com.munchybox.app.database.Products
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

import retrofit2.http.*

private const val BASE_URL = "https://api.myjson.com/bins/"

private val moshi = Moshi.Builder()
  .add(KotlinJsonAdapterFactory())
  .build()

private val retrofit = Retrofit.Builder()
  .addConverterFactory(MoshiConverterFactory.create(moshi))
  .baseUrl(BASE_URL)
  .build()

interface APIService {



  @GET("14xgtg")
  fun readItems(): Call <Products>

}

object ApiUtils {

  val apiService : APIService by lazy { retrofit.create(APIService::class.java) }

}