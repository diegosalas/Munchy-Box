package com.munchybox.app.ui.tag

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.munchybox.app.R
import com.munchybox.app.databinding.FragmentTagBinding


class TagFragment : Fragment() {

  private lateinit var binding: FragmentTagBinding

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tag, container, false)
//    val application = requireNotNull(this.activity).application
//
//    requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
//      override fun handleOnBackPressed() {
//        return
//      }
//    })
//    binding.lifecycleOwner = this

    binding.btEnterTag.setOnClickListener {
      (activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view!!.windowToken, 0)

      findNavController().navigate(R.id.global_action)
    }


    return binding.root
  }

}