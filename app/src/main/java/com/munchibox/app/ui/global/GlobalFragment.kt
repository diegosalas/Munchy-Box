
package com.munchybox.app.ui.global

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil

import androidx.fragment.app.Fragment

import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.munchybox.app.R
import com.munchybox.app.database.StoreDatabase
import com.munchybox.app.databinding.FragmentGlobalBinding


import kotlinx.android.synthetic.main.product_detail_dialog.view.*
import loadByURL

class GlobalFragment : Fragment() {

  private lateinit var binding: FragmentGlobalBinding




  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_global, container, false)
    val application = requireNotNull(this.activity).application
    val dataSource = StoreDatabase.getInstance(application).storeDatabaseDao
    val viewModelFactory =  GlobalViewModelFactory(dataSource, application)
    requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
      override fun handleOnBackPressed() {
        return
      }
    })

    //binding.imageGraph.bringToFront()
    binding.lifecycleOwner = this


    val viewModel = ViewModelProviders.of(
        this, viewModelFactory).get(GlobalViewModel::class.java)



    binding.viewModel = viewModel

    val adapter =
      GlobalAdapter(GlobalListener { code, name,price ->

        val dialog = MaterialDialog(activity!!)
          .customView(R.layout.product_detail_dialog, scrollable = true)
        val customView = dialog.getCustomView()
        customView.ivProduct.loadByURL("https://raw.githubusercontent.com/cabify/frontend-shopping-cart-challenge/master/img/cap.png")
        customView.tvproductName.text = (name)
        dialog.show()

      },AddListener { code, name, price, quantity ->
        val q = quantity + 1
        viewModel.addItem(code, name, price, q)

      },RemoveListener  { code, quantity ->
        val q = quantity - 1
        viewModel.removeItem(code, q)
      }
      )

    binding.ivGoToCart.setOnClickListener {
      findNavController().navigate(R.id.cart_action)
    }




    binding.recyclerView.adapter = adapter



    viewModel.products.observe(viewLifecycleOwner, Observer {
      it?.let {
        adapter.submitList(it)

      }
    })

    viewModel.getAccounts()


    return binding.root
  }





}


