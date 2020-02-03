
package com.cabify.cabistore.ui.global

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProviders
import com.cabify.cabistore.R

import com.cabify.cabistore.database.StoreDatabase
import com.cabify.cabistore.databinding.FragmentGlobalBinding

class GlobalFragment : Fragment() {

  private lateinit var binding: FragmentGlobalBinding


 // val list: ArrayList<Products> by lazy { getAccounts() }

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

    binding.imageGraph.bringToFront()
    binding.lifecycleOwner = this


    val viewModel = ViewModelProviders.of(
        this, viewModelFactory).get(GlobalViewModel::class.java)



    binding.viewModel = viewModel

    val adapter =
      GlobalAdapter(GlobalListener { productCode ->
        //Toast.makeText(context, "${productCode}", Toast.LENGTH_LONG).show()
      },AddListener { code, name, price ->
        viewModel.addItem(code, name, price)
      },RemoveListener  { code ->
        viewModel.removeItem(code)
      }
      )




    binding.recyclerView.adapter = adapter



    viewModel.products.observe(viewLifecycleOwner, Observer {
      it?.let {
        adapter.submitList(viewModel.list)
      }
    })



    return binding.root
  }



}