package com.munchybox.app.ui.global

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.munchybox.app.api.ApiUtils
import com.munchybox.app.database.Products
import com.munchybox.app.database.SaleDetail
import com.munchybox.app.database.StoreDatabaseDao
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import kotlin.math.ceil

class GlobalViewModel(val database: StoreDatabaseDao, application: Application) : AndroidViewModel(application) {

  private var viewModelJob = Job()
  private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)
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
//            val jsonArr = JSONArray("""{
//  "products": [
//    {
//      "code": "WEET",
//      "name": "Weet&bix",
//      "weight": 48,
//      "price": 1.5,
//      "Image": "WEET.png"
//    },
//    {
//      "code": "TIMTAM",
//      "name": "Tim Tam",
//      "weight": 18,
//      "price": 3,
//      "Image": "TIMTAM.png"
//    },
//    {
//      "code": "TICTAC",
//      "name": "Tic tac candy",
//      "weight": 24,
//      "price": 3.5,
//      "Image": "TICTAC.png"
//    },
//    {
//      "code": "ECLIPS",
//      "name": "Eclipse",
//      "weight": 27,
//      "price": 4,
//      "Image": "ECLIPS.png"
//    },
//    {
//      "code": "MENTOS",
//      "name": "Mentos",
//      "weight": 38,
//      "price": 4,
//      "Image": "MENTOS.png"
//    },
//    {
//      "code": "SKITTL",
//      "name": "Skittles",
//      "weight": 45,
//      "price": 4,
//      "Image": "SKITTL.png"
//    },
//    {
//      "code": "JELLY",
//      "name": "Jelly Belly",
//      "weight": 28,
//      "price": 4,
//      "Image": "JELLY.png"
//    },
//    {
//      "code": "DORITO",
//      "name": "Doritos",
//      "weight": 19,
//      "price": 2,
//      "Image": "DORITO.png"
//    },
//    {
//      "code": "SMITH",
//      "name": "Smiths",
//      "weight": 19,
//      "price": 2,
//      "Image": "SMITH.png"
//    },
//    {
//      "code": "NUTELL",
//      "name": "Nutella and Go",
//      "weight": 50,
//      "price": 6,
//      "Image": "NUTELL.png"
//    },
//    {
//      "code": "FREDDO",
//      "name": "Freddo",
//      "weight": 35,
//      "price": 3,
//      "Image": "FREDDO.png"
//    },
//    {
//      "code": "MALTES",
//      "name": "Maltesers",
//      "weight": 12,
//      "price": 1.5,
//      "Image": "MALTES.png"
//    },
//    {
//      "code": "KITKAT",
//      "name": "Nestle Kit Kat",
//      "weight": 45,
//      "price": 5,
//      "Image": "KITKAT.png"
//    },
//    {
//      "code": "M&M",
//      "name": "M&M Tube",
//      "weight": 35,
//      "price": 5,
//      "Image": "M&M.png"
//    },
//    {
//      "code": "SNICKE",
//      "name": "Snickers Fun Size",
//      "weight": 18,
//      "price": 2,
//      "Image": "SNICKE.png"
//    },
//    {
//      "code": "MARS",
//      "name": "Mars Fun Size",
//      "weight": 18,
//      "price": 2,
//      "Image": "MARS.png"
//    },
//    {
//      "code": "CADBUR",
//      "name": "Cadbury Dairy Milk Chocolate Bar",
//      "weight": 55,
//      "price": 5,
//      "Image": "CADBUR.png"
//    },
//    {
//      "code": "FERRER",
//      "name": "Ferrero Rochelle",
//      "weight": 12.5,
//      "price": 2,
//      "Image": "FERRER.png"
//    },
//    {
//      "code": "COKE",
//      "name": "Mini soft drink",
//      "weight": 200,
//      "price": 3,
//      "Image": "COKE.png"
//    },
//    {
//      "code": "REDBUL",
//      "name": "Red Bull",
//      "weight": 250,
//      "price": 5,
//      "Image": "REDBUL.png"
//    },
//    {
//      "code": "UP&GO",
//      "name": "Up&go",
//      "weight": 250,
//      "price": 5,
//      "Image": "UP&GO.png"
//    },
//    {
//      "code": "BOSS",
//      "name": "Boss Coffee",
//      "weight": 237,
//      "price": 7,
//      "Image": "BOSS.png"
//    },
//    {
//      "code": "EXTRA",
//      "name": "Extra",
//      "weight": 27,
//      "price": 5,
//      "Image": "EXTRA.png"
//    },
//    {
//      "code": "ALLENP",
//      "name": "Allen's Killer Pythons",
//      "weight": 192,
//      "price": 7,
//      "Image": "ALLENP.png"
//    },
//    {
//      "code": "TROLLY",
//      "name": "Trolli Britecrawlers",
//      "weight": 150,
//      "price": 5,
//      "Image": "TROLLY.png"
//    },
//    {
//      "code": "CHUPAC",
//      "name": "Chupa Chups Lollipop",
//      "weight": 12,
//      "price": 1,
//      "Image": "CHUPAC.png"
//    },
//    {
//      "code": "LUCKY",
//      "name": "Lucky",
//      "weight": 30,
//      "price": 3.5,
//      "Image": "LUCKY.png"
//    },
//    {
//      "code": "LISTER",
//      "name": "Listerine",
//      "weight": 100,
//      "price": 8,
//      "Image": "LISTER.png"
//    },
//    {
//      "code": "CONDOM",
//      "name": "Condom",
//      "weight": "-",
//      "price": 20,
//      "Image": "CONDOM.png"
//    }
//  ]
//}""")
//            val jsonObjc = jsonArr.getJSONObject(0)
//            val accountJson = jsonObjc.getJSONArray("products")
//            for (i in 0 until accountJson.length()) {
//              val code: String = accountJson.getJSONObject(i).getString("code")
//              val name = accountJson.getJSONObject(i).getString("name")
//              val price = accountJson.getJSONObject(i).getDouble("price")
//              addItem(code, name, price, 0)

  //          }
          } catch (e: IOException) {
          }
        }

      })

    } catch (e: IOException) {
      Log.e(tag, e.localizedMessage!!)
    }

  }

}

