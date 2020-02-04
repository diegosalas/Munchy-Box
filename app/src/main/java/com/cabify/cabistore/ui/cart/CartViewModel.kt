package com.cabify.cabistore.ui.cart

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import com.cabify.cabistore.database.StoreDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class CartViewModel(val database: StoreDatabaseDao, application: Application): AndroidViewModel(application) {

  var sales = database.getAllProducts()
  private var viewModelJob = Job()
  private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

}