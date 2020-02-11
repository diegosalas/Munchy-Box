package com.munchybox.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)
//    fab.setOnClickListener { view ->
//      Snackbar.make(view, "Select you payment method", Snackbar.LENGTH_LONG).setAction("Action", null).show()
//    }
  }

//  override fun onCreateOptionsMenu(menu: Menu): Boolean {
//
//    menuInflater.inflate(R.menu.menu_scrolling, menu)
//    return true
//  }

//  override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//
//    return when (item.itemId) {
//      R.id.action_settings -> true
//      else -> super.onOptionsItemSelected(item)
//    }
//  }
}
