package com.cabify.cabistore.ui.cart
import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.cabify.cabistore.database.SaleDetail
import com.cabify.cabistore.databinding.RecyclerProductsBinding


class CartAdapter(): ListAdapter<SaleDetail, CartAdapter.ViewHolder>(
SalesDiffCallback()) {

  override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
    val item = getItem(position)
    holder.bind( item)
  }
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
    return CartAdapter.ViewHolder.from(parent)
  }
  class ViewHolder private constructor(val binding: RecyclerProductsBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: SaleDetail) {
      binding.product = item


      binding.executePendingBindings()
    }
    companion object {
      fun from(parent: ViewGroup): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerProductsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
      }
    }
  }









  class SalesDiffCallback : DiffUtil.ItemCallback<SaleDetail>() {
    override fun areItemsTheSame(oldItem: SaleDetail, newItem: SaleDetail): Boolean {
      return oldItem.code == newItem.code
    }
    override fun areContentsTheSame(oldItem: SaleDetail, newItem: SaleDetail): Boolean {
      return oldItem == newItem
    }
  }
}

