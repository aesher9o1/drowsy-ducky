package com.howdy.buddy.vr.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.howdy.buddy.vr.R
import com.howdy.buddy.vr.constants.DoctorData
import com.howdy.buddy.vr.constants.PatientData
import kotlinx.android.synthetic.main.fragment_setting.*

class Setting : Fragment() {
    var currentUser ="";
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_setting, null)

        val name = v.findViewById<TextView>(R.id.name)
        val phone = v.findViewById<TextView>(R.id.phone)
        val type = arguments?.getString("type")
         currentUser = FirebaseAuth.getInstance().currentUser?.uid.toString()

        if(type =="Doctor"){
            val doctorLayout = v.findViewById<LinearLayout>(R.id.doctor)
            doctorLayout.visibility = View.VISIBLE

            val about = v.findViewById<TextView>(R.id.about)
            val address = v.findViewById<TextView>(R.id.address)

            val experience = v.findViewById<Spinner>(R.id.experience)
            ArrayAdapter.createFromResource(container!!.context, R.array.doctor_experience, android.R.layout.simple_spinner_item).also{
                    adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                experience.adapter = adapter
            }

            val submit = v.findViewById<Button>(R.id.submit)
            submit.setOnClickListener{
                val doctorData = DoctorData(name = name.text.toString(),phone = phone.text.toString(), about = about.text.toString() , address = address.text.toString(), exerience = experience.selectedItem.toString())
                    submitDoctor(doctorData)
            }

        }

        else if(type =="Patient"){

            val patientLayout = v.findViewById<LinearLayout>(R.id.patient)
            patientLayout.visibility = View.VISIBLE


            val medication = v.findViewById<TextView>(R.id.medication)

            val disease1 = v.findViewById<Spinner>(R.id.disease1)
            ArrayAdapter.createFromResource(container!!.context, R.array.doctor_experience, android.R.layout.simple_spinner_item).also{
                    adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                disease1.adapter = adapter
            }

            val disease2 = v.findViewById<Spinner>(R.id.disease2)
            ArrayAdapter.createFromResource(container.context, R.array.doctor_experience, android.R.layout.simple_spinner_item).also{
                    adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                disease2.adapter = adapter
            }


            val disease3 = v.findViewById<Spinner>(R.id.disease3)
            ArrayAdapter.createFromResource(container.context, R.array.doctor_experience, android.R.layout.simple_spinner_item).also{
                    adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                disease3.adapter = adapter



                val submit = v.findViewById<Button>(R.id.submit)
                submit.setOnClickListener{
                    val patientData = PatientData(name = name.text.toString(), phone = phone.text.toString(),medical =  medication.text.toString(), disease1 = disease1.selectedItem.toString(), disease2 = disease2.selectedItem.toString(), disease3 =  disease3.selectedItem.toString())
                        submitPatient(patientData)
                }
            }



        }


        return v
    }


  private  fun submitDoctor(data : DoctorData){

        FirebaseDatabase.getInstance().getReference("users/" + FirebaseAuth.getInstance().currentUser!!.uid+"/userType").setValue("doctor").addOnCompleteListener{
            FirebaseDatabase.getInstance().getReference("doctor/$currentUser").setValue(data).addOnCompleteListener{
                val ft: FragmentTransaction = fragmentManager!!.beginTransaction()
                ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout).replace(
                    R.id.container,
                    Dashboard(),
                    "Dashboard"
                )
                ft.commit()
            }
        }

    }

    private fun submitPatient(data : PatientData){
        FirebaseDatabase.getInstance().getReference("users/" + FirebaseAuth.getInstance().currentUser!!.uid+"/userType").setValue("patient").addOnCompleteListener{
            FirebaseDatabase.getInstance().getReference("patient/$currentUser").setValue(data)
        }
    }

}