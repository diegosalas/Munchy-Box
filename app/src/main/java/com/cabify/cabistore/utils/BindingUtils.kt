package com.cabify.cabistore.utils

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter

import com.cabify.cabistore.database.SaleDetail

@BindingAdapter("setProductName")
fun TextView.setProductName(item: SaleDetail?){
  item?.let{
    text = item.name
  }
}

@BindingAdapter("setProductCode")
fun TextView.setProductCode(item: SaleDetail?){
  item?.let{
    text = item.code
  }
}


@SuppressLint("SetTextI18n")
@BindingAdapter("setProductPrice")
fun TextView.setProductPrice(item: SaleDetail?){
  item?.let{
    text = item.price.toString() + " €"
  }
}

@BindingAdapter("setProductQty")
fun TextView.setProductQty(item: SaleDetail?){
  item?.let{
    text = item.quantity.toString()
  }
}