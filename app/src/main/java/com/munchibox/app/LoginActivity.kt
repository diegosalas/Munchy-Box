package com.munchybox.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.munchibox.app.ScanActivity

import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object{
        private const val RC_SIGN_IN = 423
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   setContentView(R.layout.activity_login)
        googleLogin()
    }

    fun googleLogin(){

        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
            ,
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build(),
            AuthUI.IdpConfig.TwitterBuilder().build()
            )


            startActivityForResult(
                AuthUI.getInstance()

                    .createSignInIntentBuilder()
                    .setLogo(R.drawable.discount)
                    .setTheme(R.style.AppThemeLogin)
                    .setAvailableProviders(providers)
                    .setIsSmartLockEnabled(false)
                    .build(),
                RC_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                Toast.makeText(this,"Bienvenid@ ${user!!.displayName} - ${user!!.uid}",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,ScanActivity::class.java))
                finish()
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                Toast.makeText(this,"Ocurrio un error ${response!!.error!!.errorCode}",Toast.LENGTH_SHORT).show()

            }
        }

    }
}