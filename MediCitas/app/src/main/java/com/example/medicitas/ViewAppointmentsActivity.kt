package com.example.medicitas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.medicitas.utils.PreferenceHelper

class ViewAppointmentsActivity : AppCompatActivity() {

    private lateinit var patientCedula: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_appointments)

        patientCedula = intent.getStringExtra("patientCedula") ?: ""

        val listView = findViewById<ListView>(R.id.listViewAppointments)
        val appointments = PreferenceHelper.getAppointmentsForPatient(this, patientCedula)

        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            appointments.map { "${it.doctor} - ${it.date} a las ${it.time}" }
        )
        listView.adapter = adapter
    }
}
