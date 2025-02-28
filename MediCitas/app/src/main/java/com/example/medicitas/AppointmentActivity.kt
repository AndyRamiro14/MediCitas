package com.example.medicitas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.medicitas.utils.PreferenceHelper

class AppointmentActivity : AppCompatActivity() {

    private lateinit var patientCedula: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)

        patientCedula = intent.getStringExtra("patientCedula") ?: ""

        val spinnerDoctor = findViewById<Spinner>(R.id.spinnerDoctor)
        val etDate = findViewById<EditText>(R.id.etDate)
        val etTime = findViewById<EditText>(R.id.etTime)
        val btnSchedule = findViewById<Button>(R.id.btnScheduleAppointment)

        // Lista de doctores predefinidos
        val doctors = listOf("Dr. Pérez", "Dr. Gómez", "Dr. Ramírez")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, doctors)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDoctor.adapter = adapter

        btnSchedule.setOnClickListener {
            val selectedDoctor = spinnerDoctor.selectedItem.toString()
            val date = etDate.text.toString()    // Ejemplo: "2025-03-10"
            val time = etTime.text.toString()    // Ejemplo: "14:00"

            if (date.isNotEmpty() && time.isNotEmpty()) {
                // Guarda la cita en SharedPreferences
                PreferenceHelper.saveAppointment(
                    this,
                    PreferenceHelper.Appointment(selectedDoctor, date, time, patientCedula)
                )
                Toast.makeText(this, "Cita agendada", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Complete fecha y hora", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
