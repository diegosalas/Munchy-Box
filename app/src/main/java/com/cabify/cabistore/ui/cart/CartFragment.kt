package com.cabify.cabistore.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProviders
import com.cabify.cabistore.R
import com.cabify.cabistore.database.StoreDatabase
import com.cabify.cabistore.databinding.FragmentCartBinding
import com.cabify.cabistore.ui.global.AddListener
import com.cabify.cabistore.ui.global.GlobalAdapter
import com.cabify.cabistore.ui.global.GlobalListener
import com.cabify.cabistore.ui.global.RemoveListener

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
    val adapter = CartAdapter(viewModel.sales)
    binding.recyclerViewCart.adapter = adapter




    viewModel.sales.observe(viewLifecycleOwner, Observer {
      it?.let {
        adapter.su


      }
    })



    return binding.root
  }
}