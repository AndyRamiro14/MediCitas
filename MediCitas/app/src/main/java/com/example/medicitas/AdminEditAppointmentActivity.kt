package com.example.medicitas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.medicitas.utils.PreferenceHelper

class AdminEditAppointmentActivity : AppCompatActivity() {

    private var appointmentIndex: Int = -1
    private lateinit var etDoctor: EditText
    private lateinit var etDate: EditText
    private lateinit var etTime: EditText
    private lateinit var etPatientCedula: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_edit_appointment)

        appointmentIndex = intent.getIntExtra("appointmentIndex", -1)
        etDoctor = findViewById(R.id.etDoctor)
        etDate = findViewById(R.id.etDate)
        etTime = findViewById(R.id.etTime)
        etPatientCedula = findViewById(R.id.etPatientCedula)
        val btnSave = findViewById<Button>(R.id.btnSave)

        // Rellenar los campos con los datos actuales
        etDoctor.setText(intent.getStringExtra("doctor") ?: "")
        etDate.setText(intent.getStringExtra("date") ?: "")
        etTime.setText(intent.getStringExtra("time") ?: "")
        etPatientCedula.setText(intent.getStringExtra("patientCedula") ?: "")

        btnSave.setOnClickListener {
            val updatedAppointment = PreferenceHelper.Appointment(
                doctor = etDoctor.text.toString(),
                date = etDate.text.toString(),
                time = etTime.text.toString(),
                patientCedula = etPatientCedula.text.toString()
            )
            PreferenceHelper.updateAppointment(this, appointmentIndex, updatedAppointment)
            finish()
        }
    }
}
