package com.cabify.cabistore.ui.cart

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cabify.cabistore.database.SaleDetail
import com.cabify.cabistore.database.StoreDatabaseDao

class CartViewModel(val database: StoreDatabaseDao, application: Application): AndroidViewModel(application) {




  private val _properties = database.getAllSales()
  val properties: LiveData<List<SaleDetail>>
    get() = _properties

  private val _total = MutableLiveData(database.getTotal())
  val total: MutableLiveData<LiveData<Double>> = _total





}