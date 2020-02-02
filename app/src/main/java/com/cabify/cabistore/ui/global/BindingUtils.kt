package com.cabify.cabistore.ui.global

import android.provider.Settings.Global.getString
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.cabify.cabistore.R
import com.cabify.cabistore.model.Products


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
