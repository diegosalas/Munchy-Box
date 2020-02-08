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

import kotlinx.coroutines.*
import org.json.JSONArray

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import kotlin.math.ceil

class GlobalViewModel(val database: StoreDatabaseDao, application: Application) : AndroidViewModel(application) {

  private var viewModelJob = Job()
  private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
  private val _total = MutableLiveData(database.getTotal())
  val total: MutableLiveData<LiveData<Double>> = _total
  private val tag = GlobalViewModel::class.java.simpleName

  private val _products = database.getAllProducts()
  val products = _products

  private suspend fun insertItem(item: SaleDetail) {
    withContext(Dispatchers.IO) {
      if (database.get(item.code) == 0) {

        database.insertItem(item)

      } else {
        if (item.quantity != 0) {
          database.updateQuantityStr(item.code, item.quantity)
          discountBuyTwoGetOne(item.code, item.quantity)
          discountBuyThree(item.code, item.quantity)
        }
      }
    }
  }

  private suspend fun deleteItem(code: String, quantity: Int) {
    withContext(Dispatchers.IO) {

      if (database.get(code) > 0) {
        if (quantity >= 0){ database.updateQuantityStr(code, quantity)
        discountBuyTwoGetOne(code, quantity)
        discountBuyThree(code, quantity)}
      }

    }

  }

  private fun discountBuyTwoGetOne(code: String, quantity: Int) {

    if (code == "VOUCHER" && quantity <= 1) {
      database.deleteItem("DISCOUNT")

    } else if (code == "VOUCHER" && (quantity % 2) == 0) {
      val decimal = (ceil(x = (quantity / 2).toDouble())).toInt()
      addItem("DISCOUNT", "2X1 Discount on Voucher", -5.00, decimal)
    }
  }

  private fun discountBuyThree(code: String, quantity: Int) {
    if (code == "TSHIRT" && quantity < 3) {
      database.updatePrice(code, 20.00)
    } else if (code == "TSHIRT" && quantity >= 3) {
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

      ApiUtils.apiService.readItems().enqueue(object : Callback<Products> {

        override fun onResponse(call: Call<Products>, response: Response<Products>) {
          if (response.isSuccessful) {

            for (i in response.body()!!.products.indices) {
              val code: String = response.body()!!.products[i].code
              val name = response.body()!!.products[i].name
              val price = response.body()!!.products[i].price

              addItem(code, name, price, 0)
            }

          }
        }

        override fun onFailure(call: Call<Products>, t: Throwable) {
          Log.e(tag, t.localizedMessage!!)
          try {
            /*

            Fill with our basic products if the customer opens the App for first time without Internet Connection

            */
            val jsonArr = JSONArray("""[{"products":[
                                           {"code":"VOUCHER","name":"Cabify Voucher","price":5},
                                           {"code":"TSHIRT","name":"Cabify T-Shirt","price":20},
                                           {"code":"MUG","name":"Cabify Coffee Mug","price":7.5}
                                            ]}]""")
            val jsonObjc = jsonArr.getJSONObject(0)
            val accountJson = jsonObjc.getJSONArray("products")
            for (i in 0 until accountJson.length()) {
              val code: String = accountJson.getJSONObject(i).getString("code")
              val name = accountJson.getJSONObject(i).getString("name")
              val price = accountJson.getJSONObject(i).getDouble("price")
              addItem(code, name, price, 0)

            }
          } catch (e: IOException) {
          }
        }

      })

    } catch (e: IOException) {
      Log.e(tag, e.localizedMessage!!)
    }

  }

}

