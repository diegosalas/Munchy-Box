package com.cabify.cabistore.utils

import android.annotation.SuppressLint
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

import com.cabify.cabistore.database.SaleDetail
import com.cabify.cabistore.ui.cart.CartAdapter


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

@SuppressLint("SetTextI18n")
@BindingAdapter("setProductSubTotal")
fun TextView.setProductSubTotal(item: SaleDetail?){
  item?.let{
    var subTotal = item.price * item.quantity
    text = "$subTotal €"
  }
}

@BindingAdapter("setProductQty")
fun TextView.setProductQty(item: SaleDetail?){
  item?.let{
    text = item.quantity.toString()
  }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<SaleDetail>?) {
  val adapter = recyclerView.adapter as CartAdapter
  adapter.submitList(data)
}

