package com.cabify.cabistore.ui.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.cabify.cabistore.database.StoreDatabaseDao

class CartViewModel(val database: StoreDatabaseDao, application: Application): AndroidViewModel(application) {


}