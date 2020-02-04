package com.cabify.cabistore.ui.global

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cabify.cabistore.api.ApiUtils
import com.cabify.cabistore.database.Products
import com.cabify.cabistore.database.SaleDetail
import com.cabify.cabistore.database.StoreDatabaseDao
import com.google.gson.JsonArray

import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.*
import org.json.JSONArray

import org.json.JSONObject
import org.json.JSONStringer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import kotlin.math.ceil
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class GlobalViewModel(val database: StoreDatabaseDao, application: Application) : AndroidViewModel(application) {

  private var viewModelJob = Job()
  private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
  private val _total = MutableLiveData(database.getTotal())
  val total: MutableLiveData<LiveData<Double>> = _total
  private val tag = GlobalViewModel::class.java.simpleName

  val products = database.getAllProducts()

  private suspend fun insertItem(item: SaleDetail) {
    withContext(Dispatchers.IO) {
      if (database.get(item.code) == 0) {


        database.insertItem(item)

      } else {

        database.updateQuantityStr(item.code, item.quantity)
        discountBuyTwoGetOne(item.code, item.quantity)
        discountBuyThree(item.code, item.quantity)
      }
    }
  }

  private suspend fun deleteItem(code: String, quantity: Int) {
    withContext(Dispatchers.IO) {

      if (database.get(code) > 0) {
        if( quantity>= 0)
          database.updateQuantityStr(code, quantity)
        discountBuyTwoGetOne(code, quantity)
        discountBuyThree(code, quantity)
      }

    }

  }

  private fun discountBuyTwoGetOne(code: String, quantity: Int){
    if(code =="VOUCHER" && quantity == 1){
      database.deleteItem("DISCOUNT")
    }else if(code =="VOUCHER" && quantity >= 2){
      val decimal = (ceil(x = (quantity / 2).toDouble())).toInt()
      addItem("DISCOUNT","2X1 Discount on Voucher", -5.00, decimal )
    }
  }

  private fun discountBuyThree(code: String, quantity: Int){
    if(code =="TSHIRT" && quantity < 3){
      database.updatePrice(code, 20.00)
    }else if(code =="TSHIRT" && quantity >= 3){
      database.updatePrice(code, 19.00)
    }
  }

  fun addItem(code: String, name: String, price: Double, quantity: Int) {
    uiScope.launch {

      val sale = SaleDetail(code, name, price, quantity)

      insertItem(sale)
    }
  }

  fun removeItem(code: String, quantity: Int) {
    uiScope.launch {
      deleteItem(code, quantity)
    }
  }

  fun getAccounts() {

    try {


      ApiUtils.apiService.readItems().enqueue(object : Callback <SaleDetail> {

        override fun onResponse(call: Call<SaleDetail>, response: Response<SaleDetail>) {
          if (response.isSuccessful) {
            Log.d(tag, "Success: ${response.body().toString()} Product Quantity")
//            val jsonArr = JSONArray("["+ response.body()+"]")
//            val jsonArr = JSONArray(response.body())
//            val jsonObjc = jsonArr.getJSONObject(0)
//            val accountJson = jsonObjc.getJSONArray("products")
//            for (i in 0 until accountJson.length()) {
//              val code: String = accountJson.getJSONObject(i).getString("code")
//              val name = accountJson.getJSONObject(i).getString("name")
//              val price = accountJson.getJSONObject(i).getDouble("price")
//              addItem(code, name, price, 0)
//            }
            addItem(response.body()!!.code,response.body()!!.name,response.body()!!.price,response.body()!!.quantity)
          }
        }


        override fun onFailure(call: Call<SaleDetail>, t: Throwable) {
          Log.e(tag,  t.localizedMessage!!)
        }



      })

    } catch (e: IOException) {
      Log.e(tag,  e.localizedMessage!!)
    }

  }

}

