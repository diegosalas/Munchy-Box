package com.munchybox.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.munchibox.app.ScanActivity


class MainActivity : AppCompatActivity() {


  private val authUser: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    goTo()
  }

  fun goTo(){
    if(authUser.currentUser != null){
      startActivity(Intent(this, ScanActivity::class.java))
      finish()
    }else{
      startActivity(Intent(this, LoginActivity::class.java))
      finish()
    }
  }
}




