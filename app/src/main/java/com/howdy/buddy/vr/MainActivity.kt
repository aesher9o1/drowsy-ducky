package com.howdy.buddy.vr


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.howdy.buddy.vr.constants.LoginUser
import com.howdy.buddy.vr.fragments.Dashboard
import com.howdy.buddy.vr.fragments.Setting


class MainActivity : AppCompatActivity() {

    val currentUser = FirebaseAuth.getInstance().currentUser
    lateinit var loginUser: LoginUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (currentUser == null)
            startActivity(Intent(this, Login::class.java))


        replaceFragment("DASHBOARD")
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("users/" + currentUser!!.uid)


        val userListener = object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                loginUser = p0.getValue(LoginUser::class.java)!!


                if (loginUser.userType == "Freebie")
                    buildLayout()
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }

        reference.addListenerForSingleValueEvent(userListener)

    }


    @SuppressLint("InflateParams")
    fun buildLayout() {


        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val inflater: LayoutInflater = this.layoutInflater
        val dialogueView: View = inflater.inflate(R.layout.dialogue_new_user, null)



        builder.setView(dialogueView)

        val alertDialog = builder.setCancelable(false).create()

        alertDialog.show()

        val patientButton = dialogueView.findViewById<Button>(R.id.patient)
        val doctorButton = dialogueView.findViewById<Button>(R.id.doctor)

        patientButton.setOnClickListener {
            alertDialog.dismiss()
            replaceFragment("PATIENT")
        }

        doctorButton.setOnClickListener {
            alertDialog.dismiss()
            replaceFragment("DOCTOR")
        }


    }


    private fun replaceFragment(mode: String) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout).replace(R.id.container, Dashboard(), "Dashboard")

        val bundle = Bundle()
        val setting = Setting()

        when (mode) {
            "PATIENT" -> {
                bundle.putString("type", "Patient")
                setting.arguments = bundle

                ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout).replace(R.id.container, setting, "Dashboard")
            }
            "DOCTOR" -> {
                bundle.putString("type", "Patient")
                setting.arguments = bundle

                ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout).replace(R.id.container, Setting(), "Dashboard")
            }
            else -> ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout).replace(
                R.id.container,
                Dashboard(),
                "Dashboard"
            )
        }

        ft.commit()
    }
}
