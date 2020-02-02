
package com.cabify.cabistore.ui.global

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProviders
import com.cabify.cabistore.R
import com.cabify.cabistore.adapter.GlobalAdapter

import com.cabify.cabistore.database.StoreDatabase
import com.cabify.cabistore.databinding.FragmentGlobalBinding
import com.cabify.cabistore.model.Products

import org.json.JSONArray
import java.io.IOException


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
    //viewModel = ViewModelProvider(this).get(GlobalViewModel::class.java)

    val viewModel = ViewModelProviders.of(
        this, viewModelFactory).get(GlobalViewModel::class.java)



    binding.viewModel = viewModel

    val adapter = GlobalAdapter()


    binding.recyclerView.adapter = adapter



    viewModel.products.observe(viewLifecycleOwner, Observer {
      it?.let {
        adapter.data = viewModel.list
      }
    })
//    viewModel.sales.observe(viewLifecycleOwner, Observer {
//      it?.let {
//        adapter.data = it as ArrayList<Products>
//      }
//    })



    return binding.root
  }


//  private fun getAccounts(): ArrayList<Products> {
//    return object : ArrayList<Products>() {
//      init {
//        try {
//          val inputStream = activity!!.assets.open("products.json")
//          val json: String? = inputStream.bufferedReader().use { it.readText() }
//          val jsonArr = JSONArray(json)
//          val jsonObjc = jsonArr.getJSONObject(0)
//          val accountJson = jsonObjc.getJSONArray("products")
//          for (i in 0 until accountJson.length()) {
//            val code: String = accountJson.getJSONObject(i).getString("code")
//            val name = accountJson.getJSONObject(i).getString("name")
//            val price = accountJson.getJSONObject(i).getInt("price")
//            add(Products(0,code, name, price,"0"))
//          }
//        } catch (e: IOException) {
//        }
//      }
//    }
//  }
}