package com.cabify.cabistore.ui.tag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cabify.cabistore.R
import com.cabify.cabistore.databinding.FragmentTagBinding

class TagFragment : Fragment() {

  private lateinit var binding: FragmentTagBinding

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return super.onCreateView(inflater, container, savedInstanceState)

    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tag, container, false)

    binding.lifecycleOwner = this

    binding.btEnterTag.setOnClickListener {

      findNavController().navigate(R.id.global_action)
    }


    return binding.root
  }

}