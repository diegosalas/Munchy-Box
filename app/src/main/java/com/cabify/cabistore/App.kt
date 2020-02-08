
package com.cabify.cabistore

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity


@SuppressLint("Registered")
class App : Application() {

  override fun onCreate() {
    super.onCreate()
    instance = this
    prefs = applicationContext.getSharedPreferences("cabStore", AppCompatActivity.MODE_PRIVATE)
    context = applicationContext

  }

  companion object {
    lateinit var instance: App
      private set
    lateinit var prefs: SharedPreferences
      private set
    lateinit var context: Context
      private set


  }
}