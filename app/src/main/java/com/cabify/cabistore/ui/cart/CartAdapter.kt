package com.cabify.cabistore.ui.cart



import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.lifecycle.LiveData

import androidx.recyclerview.widget.RecyclerView
import com.cabify.cabistore.R
import com.cabify.cabistore.database.SaleDetail
import kotlinx.android.synthetic.main.recycler_sale_detail.view.*

class CartAdapter(private val items: LiveData<List<SaleDetail>>)
  : RecyclerView.Adapter<CartAdapter.ViewHolder>() {


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val view = layoutInflater
      .inflate(R.layout.recycler_sale_detail, parent, false)

    return ViewHolder(view)
  }
  override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items)

  override fun getItemCount() = items.value!!.size


  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: LiveData<List<SaleDetail>>) = with(itemView) {

      textItem.text = item.value.toString()



    }

  }

}

