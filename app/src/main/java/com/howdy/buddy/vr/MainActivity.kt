package com.howdy.buddy.vr


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(), 0
            )
            Toast.makeText(this, "Starting Ui", Toast.LENGTH_LONG).show()
        } else
            Log.w("Main Activity", FirebaseAuth.getInstance().currentUser.toString())


    }
}
