package com.example.medicitas

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class HomeActivity : AppCompatActivity() {

    private lateinit var patientCedula: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        patientCedula = intent.getStringExtra("patientCedula") ?: ""

        val btnNewAppointment = findViewById<Button>(R.id.btnNewAppointment)
        val btnViewAppointments = findViewById<Button>(R.id.btnViewAppointments)

        btnNewAppointment.setOnClickListener {
            val intent = Intent(this, AppointmentActivity::class.java)
            intent.putExtra("patientCedula", patientCedula)
            startActivity(intent)
        }

        btnViewAppointments.setOnClickListener {
            val intent = Intent(this, ViewAppointmentsActivity::class.java)
            intent.putExtra("patientCedula", patientCedula)
            startActivity(intent)
        }
    }
}
