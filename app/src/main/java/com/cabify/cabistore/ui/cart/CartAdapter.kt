package com.cabify.cabistore.ui.cart


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cabify.cabistore.R
import com.cabify.cabistore.database.SaleDetail

class CartAdapter: RecyclerView.Adapter<CartAdapter.ViewHolder>() {


  var data = listOf<SaleDetail>()
    set(value) {
      field = value
      notifyDataSetChanged()
    }



  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val view = layoutInflater
      .inflate(R.layout.recycler_sale_detail, parent, false)
    return ViewHolder(view)
  }

  override fun getItemCount() = data.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = data[position]
    holder.itemAmount.text = item.name
    holder.itemQty.text = item.quantity.toString()

  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val itemName: TextView = itemView.findViewById(R.id.textItem)
    val itemQty: TextView = itemView.findViewById(R.id.tvItemQty)
    val itemAmount: TextView = itemView.findViewById(R.id.tvItemsAmount)
  }
}