package com.munchybox.app.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProviders
import com.munchybox.app.CheckoutActivity


import com.munchybox.app.R
import com.munchybox.app.database.StoreDatabase
import com.munchybox.app.databinding.FragmentCartBinding
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams


class CartFragment : Fragment() {

  private lateinit var binding: FragmentCartBinding


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
    val application = requireNotNull(this.activity).application
    val dataSource = StoreDatabase.getInstance(application).storeDatabaseDao
    val viewModelFactory =  CartViewModelFactory(dataSource, application)

    binding.lifecycleOwner = this

    val viewModel = ViewModelProviders.of(
      this, viewModelFactory).get(CartViewModel::class.java)

    binding.viewModel = viewModel

    binding.recyclerView.adapter =  CartAdapter()

    binding.addCard.setOnClickListener {

      val intent = Intent(activity!!, CheckoutActivity::class.java)
      intent.putExtra("total", binding.tvTotalPay.text.toString())

      startActivity(intent)

    }







    return binding.root
  }


}