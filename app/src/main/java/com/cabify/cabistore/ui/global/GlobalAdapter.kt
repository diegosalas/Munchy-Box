package com.cabify.cabistore.ui.global

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView




import com.cabify.cabistore.database.SaleDetail
import com.cabify.cabistore.databinding.RecyclerProductsBinding

class GlobalAdapter(val clickListener: GlobalListener, val addClickListener: AddListener, val removeClickListener: RemoveListener) : ListAdapter<SaleDetail, GlobalAdapter.ViewHolder>(
  ProductsDiffCallback()) {

  //  var data = listOf<Products>()
  //    set(value) {
  //      field = value
  //
  //      notifyDataSetChanged()
  //    }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = getItem(position)
    holder.bind( clickListener, addClickListener, removeClickListener, item)

  }


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    return ViewHolder.from(parent)
  }

  //  override fun getItemCount(): Int {
  //
  //    return data.size
  //  }



  class ViewHolder private constructor(val binding: RecyclerProductsBinding) : RecyclerView.ViewHolder(binding.root) {


    fun bind( clickListener: GlobalListener,addClickListener: AddListener,removeClickListener: RemoveListener, item: SaleDetail) {

        binding.product = item
        binding.clickListener = clickListener
        binding.addClickListener = addClickListener
        binding.removeClickListener = removeClickListener
        binding.executePendingBindings()




      /*binding.addButton.setOnClickListener {

        if (adapterPosition >= 0 && adapterPosition < App.quantityOrdered.size) {
          App.quantityOrdered[position]++
          binding.quantityTextView.text = App.quantityOrdered[position].toString()
          val total = App.prefs.getInt("total", 0)
          App.prefs.commit {
            putInt("total",total + (item.quantity * item.price))
          }
        }

      }
      binding.removeButton.setOnClickListener {
        if (App.quantityOrdered[position] > 0) {
          App.quantityOrdered[position]--
          binding.quantityTextView.text = App.quantityOrdered[position].toString()
          val total = App.prefs.getInt("total", 0)
          App.prefs.commit {
            putInt("total",total - (item.quantity * item.price))
          }


        }

      }*/
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
class GlobalListener(val clickListener: (productCode: String) ->Unit){
  fun onClick(product: SaleDetail) = clickListener(product.code)
}
class AddListener(val AddClickListener: (productCode: String, productName: String, productPrice: Int, productQty: Int) ->Unit){
  fun onClick(product: SaleDetail) = AddClickListener(product.code, product.name, product.price, product.quantity)
}
class RemoveListener(val RemoveClickListener: (productCode: String, productQty: Int) ->Unit){
  fun onClick(product: SaleDetail) = RemoveClickListener(product.code, product.quantity)
}