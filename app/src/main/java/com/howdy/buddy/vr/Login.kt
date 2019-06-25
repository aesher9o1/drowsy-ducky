package com.howdy.buddy.vr

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.howdy.buddy.vr.constants.LoginUser
import java.util.*

class Login : AppCompatActivity() {

    private lateinit var login: Button

    private val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login = findViewById(R.id.login)


        login.setOnClickListener {
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(), 0
            )
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {

            Snackbar.make(login, "Please wait while we log you in", Snackbar.LENGTH_LONG)
            val reference: DatabaseReference =
                FirebaseDatabase.getInstance().getReference("users/" + FirebaseAuth.getInstance().currentUser!!.uid)
            val loginUser = LoginUser(false, Calendar.getInstance().timeInMillis.toString(), "Freebie")

            reference.setValue(loginUser).addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
            }


        }
    }

}
