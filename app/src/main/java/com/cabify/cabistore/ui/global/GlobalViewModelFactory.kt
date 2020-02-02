package com.cabify.cabistore.ui.global

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cabify.cabistore.database.StoreDatabaseDao

class GlobalViewModelFactory(
  private val dataSource: StoreDatabaseDao,
  private val application: Application) : ViewModelProvider.Factory {
  @Suppress("unchecked_cast")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(GlobalViewModel::class.java)) {
      return GlobalViewModel(dataSource, application) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}