package com.cabify.cabistore.ui.global

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cabify.cabistore.database.SaleDetail
import com.cabify.cabistore.databinding.RecyclerProductsBinding



class GlobalAdapter(val clickListener: GlobalListener, val addClickListener: AddListener, val removeClickListener: RemoveListener) :
  ListAdapter<SaleDetail, GlobalAdapter.ViewHolder>(ProductsDiffCallback()) {
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = getItem(position)
    holder.bind(clickListener, addClickListener, removeClickListener, item)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder.from(parent)
  }

  class ViewHolder private constructor(val binding: RecyclerProductsBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(clickListener: GlobalListener, addClickListener: AddListener, removeClickListener: RemoveListener, item: SaleDetail) {
      binding.product = item
      if (item.code == "DISCOUNT") {
        binding.executePendingBindings()
        binding.addButton.visibility = View.INVISIBLE
        binding.removeButton.visibility = View.INVISIBLE
      }else{
        binding.addButton.visibility = View.VISIBLE
        binding.removeButton.visibility = View.VISIBLE
        binding.executePendingBindings()
      }
      binding.clickListener = clickListener
      binding.addClickListener = addClickListener
      binding.removeClickListener = removeClickListener
    }

    companion object {
      fun from(parent: ViewGroup): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerProductsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
      }
    }
  }

  class ProductsDiffCallback : DiffUtil.ItemCallback<SaleDetail>() {
    override fun areItemsTheSame(oldItem: SaleDetail, newItem: SaleDetail): Boolean {
      return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: SaleDetail, newItem: SaleDetail): Boolean {
      return oldItem == newItem
    }
  }
}

class GlobalListener(val clickListener: (productCode: String) -> Unit) {
  fun onClick(product: SaleDetail) = clickListener(product.code)
}

class AddListener(val AddClickListener: (productCode: String, productName: String, productPrice: Double, productQty: Int) -> Unit) {
  fun onClick(product: SaleDetail) {
    AddClickListener(product.code, product.name, product.price, product.quantity)

  }
}

class RemoveListener(val RemoveClickListener: (productCode: String, productQty: Int) -> Unit) {
  fun onClick(product: SaleDetail) = RemoveClickListener(product.code, product.quantity)
}