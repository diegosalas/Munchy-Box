package com.cabify.cabistore.ui.cart

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import com.cabify.cabistore.database.StoreDatabaseDao

class CartViewModel(val database: StoreDatabaseDao, application: Application): AndroidViewModel(application) {

  var sales = database.getAllProducts()


}