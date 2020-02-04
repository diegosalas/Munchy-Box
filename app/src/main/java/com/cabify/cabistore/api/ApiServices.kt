
package com.cabify.cabistore.api



import com.cabify.cabistore.database.Products
import com.cabify.cabistore.database.SaleDetail
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONStringer
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import java.util.*
import java.util.jar.JarEntry

private const val BASE_URL = "https://api.myjson.com/bins/"

private val moshi = Moshi.Builder()
  .add(ProductAdapter())
  .add(KotlinJsonAdapterFactory())
  .build()

val adapter: JsonAdapter<SaleDetail> = moshi.adapter(SaleDetail::class.java)


private val retrofit = Retrofit.Builder()
  //.addConverterFactory(MoshiConverterFactory.create(moshi))
  .addConverterFactory(GsonConverterFactory.create())
  .baseUrl(BASE_URL)
  .build()

interface APIService {


  @GET("4bwec")
  fun readItems(): Call <SaleDetail>


}

object ApiUtils {

    val apiService : APIService by lazy { retrofit.create(APIService::class.java) }

}