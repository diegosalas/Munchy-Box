package com.munchybox.app


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.munchybox.app.R
import kotlinx.android.synthetic.main.activity_main.*



class Principal : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


    }
}

