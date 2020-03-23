package com.munchybox.app


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.stripe.android.Stripe
import kotlinx.android.synthetic.main.activity_checkout.*


class CheckoutActivity : AppCompatActivity() {

    private lateinit var paymentIntentClientSecret: String
    private lateinit var stripe: Stripe
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val total = intent.getStringExtra("total")
        setContentView(R.layout.activity_checkout)

        tvSubTotal.text = "$total $"



    }


}

