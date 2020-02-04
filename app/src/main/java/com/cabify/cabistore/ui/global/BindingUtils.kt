package com.cabify.cabistore.ui.global

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.cabify.cabistore.database.Products


@BindingAdapter("setProductName")
fun TextView.setProductName(item: Products?){
  item?.let{
    text = item.name
  }
}

@BindingAdapter("setProductCode")
fun TextView.setProductCode(item: Products?){
  item?.let{
    text = item.code
  }
}


@BindingAdapter("setProductPrice")
fun TextView.setProductPrice(item: Products?){
  item?.let{
    text = item.price.toString() + " €"
  }
}

@BindingAdapter("setProductQty")
fun TextView.setProductQty(item: Products?){
  item?.let{
    text = item.quantity.toString()
  }
}