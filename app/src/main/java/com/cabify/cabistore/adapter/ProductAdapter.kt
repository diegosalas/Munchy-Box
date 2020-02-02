package com.cabify.cabistore.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cabify.cabistore.App.Companion.bill
import com.cabify.cabistore.R
import com.cabify.cabistore.model.Products
import kotlinx.android.synthetic.main.recycler_products.view.*
import kotlinx.android.synthetic.main.recycler_products.view.textCode
import kotlinx.android.synthetic.main.recycler_products.view.textProductName
import kotlinx.android.synthetic.main.recycler_products.view.textAmount

class ProductAdapter(private val products: ArrayList<Products>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
  var data = listOf<Products>()

  private val quantityOrdered: IntArray = IntArray(products.size)
  private var totalOrdered: Int = 0
  override fun getItemCount() = products.size
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(product = products[position])
  }
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    val mView: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_products, parent, false)
    return ViewHolder(mView, this)
  }

  override fun getItemViewType(position: Int): Int {
    return position
  }

  class ViewHolder(itemView: View, adapter: ProductAdapter) : RecyclerView.ViewHolder(itemView) {

    private val quantityTextView: TextView = itemView.findViewById(R.id.quantityTextView)
    private val addButton: ImageView = itemView.findViewById(R.id.addButton)
    private val removeButton: ImageView = itemView.findViewById(R.id.removeButton)

    init {
      addButton.setOnClickListener {
        val q = adapter.bumpItemQuantity(adapterPosition, true)
        this.quantityTextView.text = q.toString()

      }
      removeButton.setOnClickListener {
        val q = adapter.bumpItemQuantity(adapterPosition, false)
        this.quantityTextView.text = q.toString()

      }

    }

    @SuppressLint("SetTextI18n")
    fun bind(product: Products) = with(itemView) {
      textProductName.text = product.name
      textAmount.text = product.price.toString() + " €"
      textCode.text = product.code
      product.quantity = quantityTextView.text as String
      Log.d("Product Adapter", "products: $product")
      Log.d("Product Adapter", "bill: ${bill.total}")

    }

  }

  private fun bumpItemQuantity(index: Int, increase: Boolean): Int {

    if (index >= 0 && index < quantityOrdered.size) {
      if (increase) {
        quantityOrdered[index]++
        totalOrdered++
        Log.d("ProductAdapter", "$index : ${quantityOrdered[index]}")

        //  totalItemsChangedListener.onTotalItemsChanged(totalOrdered)
      } else if (quantityOrdered[index] > 0) {
        quantityOrdered[index]--
        totalOrdered--
        Log.d("ProductAdapter", "$index : ${quantityOrdered[index]} ")

        // totalItemsChangedListener.onTotalItemsChanged(totalOrdered)
      }
    }

    notifyDataSetChanged()
    return quantityOrdered[index]
  }

}


