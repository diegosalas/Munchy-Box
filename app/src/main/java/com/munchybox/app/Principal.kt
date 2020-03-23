package com.munchybox.app


import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.gson.GsonBuilder
import com.munchybox.app.R
import com.stripe.android.ApiResultCallback
import com.stripe.android.PaymentIntentResult
import com.stripe.android.Stripe
import com.stripe.android.model.StripeIntent
import com.stripe.android.view.CardInputWidget
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.product_detail_dialog.view.*
import java.lang.ref.WeakReference


class Principal : AppCompatActivity() {

    private lateinit var paymentIntentClientSecret: String
    private lateinit var stripe: Stripe
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val weakActivity = WeakReference<Activity>(this)

        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, object : ApiResultCallback<PaymentIntentResult> {
            override fun onSuccess(result: PaymentIntentResult) {
                val paymentIntent = result.intent
                val status = paymentIntent.status
                if (status == StripeIntent.Status.Succeeded) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    displayAlert(weakActivity.get(), "Payment succeeded", gson.toJson(paymentIntent), restartDemo = true)
                } else {
                    displayAlert(weakActivity.get(), "Payment failed", paymentIntent.lastPaymentError?.message ?: "")
                }
            }

            override fun onError(e: Exception) {
                displayAlert(weakActivity.get(), "Payment failed", e.toString())
            }
        })
    }

    private fun displayAlert(activity: Activity?, title: String, message: String, restartDemo: Boolean = false) {
        if (activity == null) {
            return
        }
        runOnUiThread {
            val builder = AlertDialog.Builder(activity!!)
            builder.setTitle(title)
            builder.setMessage(message)
            if (restartDemo) {
                builder.setPositiveButton("Restart demo") { _, _ ->
                    val cardInputWidget =
                        findViewById<CardInputWidget>(R.id.cardInputWidget)
                    cardInputWidget.clear()
                   // startCheckout()
                }
            }
            else {
                builder.setPositiveButton("Ok", null)
            }
            val dialog = builder.create()
            dialog.show()
        }
    }

}

