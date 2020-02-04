package com.cabify.cabistore.ui.global

import android.app.Application
import android.provider.SyncStateContract.Helpers.update

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.cabify.cabistore.database.SaleDetail
import com.cabify.cabistore.database.StoreDatabaseDao

import kotlinx.coroutines.*
import org.json.JSONArray
import java.io.IOException

class GlobalViewModel(val database: StoreDatabaseDao, application: Application) : AndroidViewModel(application) {
  //val list: ArrayList<SaleDetail> by lazy { getAccounts() }



  private var viewModelJob = Job()
  private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
  private val _total = MutableLiveData(database.getTotal())
  val total: MutableLiveData<LiveData<Int>> = _total


  val sales = database.getAllSales()
  val products = database.getAllProducts()

  private suspend fun insertItem(item: SaleDetail) {
    withContext(Dispatchers.IO) {
      if (database.get(item.code) == 0) {
       // if (item.quantity == 0) {
          item.quantity = database.get(item.code)
          database.insertItem(item)

        } else {
          //item.quantity = database.get(item.code)
          database.updateQuantityStr(item.code, item.quantity)

        }
      }
    }
  //}



  private suspend fun deleteItem(code: String, quantity :Int) {
    withContext(Dispatchers.IO) {

      if(database.get(code)>0){
        database.updateQuantityStr(code,quantity )
      } else if (database.get(code)==1){
        //database.deleteItem(code)
      }
      }

  }



 fun addItem(code: String, name:String, price: Int, quantity: Int){
   uiScope.launch {

      var sale = SaleDetail(code, name, price, quantity)

      insertItem(sale)
    }
  }

  fun removeItem(code: String, quantity: Int){
    uiScope.launch {
      deleteItem(code, quantity)
    }
  }


  fun getAccounts(){
   // return object : ArrayList<SaleDetail>() {
     // init {
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
            addItem(code,name,price,0)




          }
        } catch (e: IOException) {
        }
     // }
    //}
  }

}

