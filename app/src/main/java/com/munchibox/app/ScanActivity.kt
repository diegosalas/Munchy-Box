package com.munchibox.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.munchybox.app.MainActivity
import com.munchybox.app.Principal
import com.munchybox.app.R.layout.activity_scan
import kotlinx.android.synthetic.main.activity_scan.*
import kotlinx.android.synthetic.main.activity_scan.btEnterTag
import kotlinx.android.synthetic.main.activity_scan.textInputLayout
import kotlinx.android.synthetic.main.activity_scan.tvEnterTag
import kotlinx.android.synthetic.main.activity_scan.txTag
import kotlinx.android.synthetic.main.fragment_tag.*

class ScanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_scan)
        btnScan.setOnClickListener {
            run {
                IntentIntegrator(this@ScanActivity).initiateScan();
            }
        }
        btEnterTag.setOnClickListener {

          if (txTag.text.toString() =="000061") {
              startActivity(Intent(this, Principal::class.java))
              finish()
          }

        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        var result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if(result != null){

            if(result.contents != null && result.contents=="000061"){
                tvEnterTag.text = result.contents
                startActivity(Intent(this, Principal::class.java))
                finish()

            } else {
                tvEnterTag.text = "Enter Tag Maually"
                tvEnterTag.visibility = View.VISIBLE
                textInputLayout.visibility = View.VISIBLE
                txTag.visibility = View.VISIBLE
                imageQR.visibility = View.GONE
                btEnterTag.visibility = View.VISIBLE
                btnScan.visibility = View.GONE




            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}