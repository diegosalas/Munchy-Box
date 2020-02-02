
package com.cabify.cabistore

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.cabify.cabistore.model.Bill
import com.cabify.cabistore.model.Products

@SuppressLint("Registered")
class App : Application() {



  override fun onCreate() {
    super.onCreate()

    prefs = applicationContext.getSharedPreferences("cabStore", AppCompatActivity.MODE_PRIVATE)
    context = applicationContext



  }

  companion object {
    lateinit var prefs: SharedPreferences
      private set
    lateinit var context: Context
      private set
    var bill = Bill()

    var p = listOf<Products>()
    //val quantityOrdered: IntArray = IntArray(p.size)
    var quantityOrdered: IntArray = IntArray(3)


  }
}