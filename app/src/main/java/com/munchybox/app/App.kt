
package com.munchybox.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.stripe.android.PaymentConfiguration

@SuppressLint("Registered")
class App : Application() {

  override fun onCreate() {
    super.onCreate()
    PaymentConfiguration.init(
      applicationContext,
      "pk_test_b9b5KIQJDgjC6rWY9LI07dPR"
    )
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