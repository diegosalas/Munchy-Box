package com.munchybox.app.utils

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.munchybox.app.database.SaleDetail
import com.munchybox.app.ui.cart.CartAdapter


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
    text = item.price.toString() + " $"
  }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("setProductSubTotal")
fun TextView.setProductSubTotal(item: SaleDetail?){
  item?.let{
    var subTotal = item.price * item.quantity
    text = "$subTotal $"
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

