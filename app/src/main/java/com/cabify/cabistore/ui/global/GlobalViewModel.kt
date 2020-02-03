package com.cabify.cabistore.ui.global

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cabify.cabistore.App
import com.cabify.cabistore.database.SaleDetail
import com.cabify.cabistore.database.StoreDatabaseDao
import com.cabify.cabistore.database.Products
import kotlinx.coroutines.*
import org.json.JSONArray
import java.io.IOException

class GlobalViewModel(val database: StoreDatabaseDao, application: Application) : AndroidViewModel(application) {
  val list: ArrayList<Products> by lazy { getAccounts() }
  private var viewModelJob = Job()
  private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
  private val _total = MutableLiveData(database.getTotal())
  val total: MutableLiveData<LiveData<Int>> = _total


  val sales = database.getAllSales()
  val products = database.getAllProducts()
  private suspend fun insertItem(item: SaleDetail) {
    withContext(Dispatchers.IO) {
      if(database.get(item.code) == 0){
          item.quantity = database.get(item.code) + 1
          database.insertItem(item)}
      else{
        item.quantity = database.get(item.code) + 1
        database.updateQuantityStr(item.code,database.get(item.code) + 1)
      }
    }
  }

  private suspend fun deleteItem(code: String) {
    withContext(Dispatchers.IO) {

      if(database.get(code)>1){
        database.updateQuantityStr(code, database.get(code) - 1)
      } else if (database.get(code)==1){
        database.deleteItem(code)
      }
      }

  }

 fun addItem(code: String, name:String, price: Int){


   uiScope.launch {

      var sale = SaleDetail(code, name, price, 1)
      insertItem(sale)
    }
  }

  fun removeItem(code: String){
    uiScope.launch {
      deleteItem(code)
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
            add(Products(0, code, name, price, 0))
          }
        } catch (e: IOException) {
        }
      }
    }
  }

}

