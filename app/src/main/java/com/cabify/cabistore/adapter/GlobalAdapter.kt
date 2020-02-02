package com.cabify.cabistore.adapter



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cabify.cabistore.R
import com.cabify.cabistore.model.Products

class GlobalAdapter() : RecyclerView.Adapter<GlobalAdapter.ViewHolder>() {


  var data = listOf<Products>()
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  override fun getItemCount() = data.size
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = data[position]
    holder.code.text = item.code
    holder.nameProduct.text = item.name
    holder.price.text = item.price.toString()


  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val view = layoutInflater.inflate(R.layout.recycler_products, parent, false)
    return ViewHolder(view)
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val nameProduct: TextView = itemView.findViewById(R.id.textProductName)
    val price: TextView = itemView.findViewById(R.id.textAmount)
    val code: TextView = itemView.findViewById(R.id.textCode)
  }

}