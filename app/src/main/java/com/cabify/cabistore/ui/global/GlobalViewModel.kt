package com.cabify.cabistore.ui.global

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cabify.cabistore.database.SaleDetail
import com.cabify.cabistore.database.StoreDatabaseDao
import kotlinx.coroutines.*
import org.json.JSONArray
import java.io.IOException
import kotlin.math.ceil

class GlobalViewModel(val database: StoreDatabaseDao, application: Application) : AndroidViewModel(application) {

  private var viewModelJob = Job()
  private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
  private val _total = MutableLiveData(database.getTotal())
  val total: MutableLiveData<LiveData<Double>> = _total

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

}

