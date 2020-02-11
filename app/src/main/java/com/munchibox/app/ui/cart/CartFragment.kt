package com.munchybox.app.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProviders



import com.munchybox.app.R
import com.munchybox.app.database.StoreDatabase
import com.munchybox.app.databinding.FragmentCartBinding


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







    return binding.root
  }
}