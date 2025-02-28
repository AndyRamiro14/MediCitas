package com.example.medicitas

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.medicitas.utils.PreferenceHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AdminActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private var appointments: MutableList<PreferenceHelper.Appointment> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        listView = findViewById(R.id.listViewAllAppointments)
        loadAppointments()

        listView.setOnItemClickListener { _, _, position, _ ->
            val appointment = appointments[position]
            AlertDialog.Builder(this)
                .setTitle("Acciones para la cita")
                .setItems(arrayOf("Editar cita", "Cancelar cita")) { dialog, which ->
                    when (which) {
                        0 -> { // Editar cita
                            val intent = Intent(this, AdminEditAppointmentActivity::class.java)
                            intent.putExtra("appointmentIndex", position)
                            intent.putExtra("doctor", appointment.doctor)
                            intent.putExtra("date", appointment.date)
                            intent.putExtra("time", appointment.time)
                            intent.putExtra("patientCedula", appointment.patientCedula)
                            startActivity(intent)
                        }
                        1 -> { // Cancelar cita
                            AlertDialog.Builder(this)
                                .setTitle("Confirmar cancelación")
                                .setMessage("¿Estás seguro de cancelar esta cita?")
                                .setPositiveButton("Sí") { d, _ ->
                                    PreferenceHelper.deleteAppointment(this, position)
                                    loadAppointments()
                                    d.dismiss()
                                }
                                .setNegativeButton("No") { d, _ -> d.dismiss() }
                                .show()
                        }
                    }
                }
                .show()
        }
    }

    private fun loadAppointments() {
        appointments = getAllAppointments().toMutableList()
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            appointments.map { appointment ->
                "Paciente: ${appointment.patientCedula}\nDoctor: ${appointment.doctor}\nFecha: ${appointment.date} - ${appointment.time}"
            }
        )
        listView.adapter = adapter
    }

    private fun getAllAppointments(): List<PreferenceHelper.Appointment> {
        val prefs = PreferenceHelper.getPrefs(this)
        val appointmentsJson = prefs.getString("appointments", null) ?: return emptyList()
        val gson = Gson()
        val type = object : TypeToken<List<PreferenceHelper.Appointment>>() {}.type
        return gson.fromJson(appointmentsJson, type)
    }
}
