package com.cabify.cabistore.ui.cart

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cabify.cabistore.database.StoreDatabaseDao


class CartViewModelFactory (

  private val dataSource: StoreDatabaseDao,
  private val application: Application) : ViewModelProvider.Factory {
  @Suppress("unchecked_cast")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
      return CartViewModel(dataSource, application) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}