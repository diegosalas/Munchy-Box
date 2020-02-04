package com.cabify.cabistore.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.cabify.cabistore.R
import com.cabify.cabistore.database.StoreCart
import com.cabify.cabistore.database.StoreLineItem
import com.cabify.cabistore.databinding.FragmentPaymentBinding
import java.util.*

class paymentFragment :Fragment() {
  private lateinit var binding: FragmentPaymentBinding
  private lateinit var storeCart: StoreCart

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false)
    addCartItems()

    return binding.root
  }

  private fun addCartItems() {
    binding.cartItemLayout.removeAllViewsInLayout()
    val currencySymbol = storeCart.currency.getSymbol(Locale.US)

    addLineItems(currencySymbol, storeCart.lineItems)

  //  addLineItems(currencySymbol, listOf(StoreLineItem(getString(R.string.checkout_shipping_cost_label), 1, shippingCosts)))

    val totalView = layoutInflater
      .inflate(R.layout.cart_item, binding.cartItemLayout, false)

    //setupTotalPriceView(totalView, currencySymbol)
    binding.cartItemLayout.addView(totalView)
  }

  private fun addLineItems(currencySymbol: String, items: List<StoreLineItem>) {
    for (item in items) {
      val view = layoutInflater.inflate(
        R.layout.cart_item, binding.cartItemLayout, false)
      //fillOutCartItemView(item, view, currencySymbol)
      binding.cartItemLayout.addView(view)
    }
  }
}