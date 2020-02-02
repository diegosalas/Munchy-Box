package com.cabify.cabistore.ui.global

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import com.cabify.cabistore.database.SaleDetail
import com.cabify.cabistore.database.StoreDatabaseDao
import com.cabify.cabistore.model.Products
import kotlinx.coroutines.*
import org.json.JSONArray
import java.io.IOException

class GlobalViewModel(val database: StoreDatabaseDao, application: Application) : AndroidViewModel(application) {
  val list: ArrayList<Products> by lazy { getAccounts() }
  private var viewModelJob = Job()
  private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

  val total = database.getTotal()

  val sales = database.getAllSales()
  val products = database.getAllProducts()
  private suspend fun insertItem(item: SaleDetail) {
    withContext(Dispatchers.IO) {
      database.insertItem(item)
    }
  }

  private suspend fun insertProduct(product: Products) {
    withContext(Dispatchers.IO) {
      //database.insertProduct(list)
    }
  }

  init {
    uiScope.launch {
      var sale = SaleDetail()
      insertItem(sale)
    }
  }

  private fun getAccounts(): ArrayList<Products> {
    return object : ArrayList<Products>() {
      init {
        try {

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
            val price = accountJson.getJSONObject(i).getInt("price")
            add(Products(0, code, name, price, "0"))
          }
        } catch (e: IOException) {
        }
      }
    }
  }

}

