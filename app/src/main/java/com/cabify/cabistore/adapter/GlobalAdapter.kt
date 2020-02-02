package com.cabify.cabistore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cabify.cabistore.App
import com.cabify.cabistore.databinding.RecyclerProductsBinding

import com.cabify.cabistore.model.Products

class GlobalAdapter(val clickListener: GlobalListener) : ListAdapter<Products, GlobalAdapter.ViewHolder>(ProductsDiffCallback()) {

  //  var data = listOf<Products>()
  //    set(value) {
  //      field = value
  //
  //      notifyDataSetChanged()
  //    }
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    return ViewHolder.from(parent)
  }

  //  override fun getItemCount(): Int {
  //
  //    return data.size
  //  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = getItem(position)
    holder.bind( clickListener, item)

  }

  class ViewHolder private constructor(val binding: RecyclerProductsBinding) : RecyclerView.ViewHolder(binding.root) {


    fun bind( clickListener: GlobalListener, item: Products) {

        binding.product = item
        binding.clickListener = clickListener
        binding.executePendingBindings()




      binding.addButton.setOnClickListener {

        if (adapterPosition >= 0 && adapterPosition < App.quantityOrdered.size) {
          App.quantityOrdered[position]++
          binding.quantityTextView.text = App.quantityOrdered[position].toString()

        }

      }
      binding.removeButton.setOnClickListener {
        if (App.quantityOrdered[position] > 0) {
          App.quantityOrdered[position]--
          binding.quantityTextView.text = App.quantityOrdered[position].toString()

        }

      }
    }

    companion object {
      fun from(parent: ViewGroup): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerProductsBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
      }
    }
  }

  class ProductsDiffCallback : DiffUtil.ItemCallback<Products>() {
    override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
      return oldItem.productId == newItem.productId
    }

    override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
      return oldItem == newItem
    }

  }





}
class GlobalListener(val clickListener: (productCode: String) ->Unit){
  fun onClick(product:Products) = clickListener(product.code)
}