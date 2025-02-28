package com.example.medicitas.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PreferenceHelper {
    private const val PREFS_NAME = "medicitas_prefs"
    private const val KEY_PATIENTS = "patients"
    private const val KEY_APPOINTMENTS = "appointments"

    fun getPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // --- Pacientes ---
    fun savePatient(context: Context, name: String, cedula: String) {
        val prefs = getPrefs(context)
        val patientsJson = prefs.getString(KEY_PATIENTS, null)
        val gson = Gson()
        val type = object : TypeToken<MutableMap<String, String>>() {}.type
        val patients: MutableMap<String, String> =
            if (patientsJson != null) gson.fromJson(patientsJson, type) else mutableMapOf()

        // Usamos la cédula como clave y el nombre como valor
        patients[cedula] = name

        prefs.edit().putString(KEY_PATIENTS, gson.toJson(patients)).apply()
    }

    fun isPatientRegistered(context: Context, cedula: String): Boolean {
        val prefs = getPrefs(context)
        val patientsJson = prefs.getString(KEY_PATIENTS, null) ?: return false
        val gson = Gson()
        val type = object : TypeToken<Map<String, String>>() {}.type
        val patients: Map<String, String> = gson.fromJson(patientsJson, type)
        return patients.containsKey(cedula)
    }

    // --- Citas ---
    data class Appointment(val doctor: String, val date: String, val time: String, val patientCedula: String)

    fun saveAppointment(context: Context, appointment: Appointment) {
        val prefs = getPrefs(context)
        val appointmentsJson = prefs.getString(KEY_APPOINTMENTS, null)
        val gson = Gson()
        val type = object : TypeToken<MutableList<Appointment>>() {}.type
        val appointments: MutableList<Appointment> =
            if (appointmentsJson != null) gson.fromJson(appointmentsJson, type) else mutableListOf()

        appointments.add(appointment)
        prefs.edit().putString(KEY_APPOINTMENTS, gson.toJson(appointments)).apply()
    }

    fun getAppointmentsForPatient(context: Context, patientCedula: String): List<Appointment> {
        val prefs = getPrefs(context)
        val appointmentsJson = prefs.getString(KEY_APPOINTMENTS, null) ?: return emptyList()
        val gson = Gson()
        val type = object : TypeToken<List<Appointment>>() {}.type
        val appointments: List<Appointment> = gson.fromJson(appointmentsJson, type)
        return appointments.filter { it.patientCedula == patientCedula }
    }

    // Funciones para el administrador: actualizar y eliminar citas

    fun updateAppointment(context: Context, index: Int, updatedAppointment: Appointment) {
        val prefs = getPrefs(context)
        val appointmentsJson = prefs.getString(KEY_APPOINTMENTS, null)
        val gson = Gson()
        val type = object : TypeToken<MutableList<Appointment>>() {}.type
        val appointments: MutableList<Appointment> =
            if (appointmentsJson != null) gson.fromJson(appointmentsJson, type) else mutableListOf()
        if (index in appointments.indices) {
            appointments[index] = updatedAppointment
            prefs.edit().putString(KEY_APPOINTMENTS, gson.toJson(appointments)).apply()
        }
    }

    fun deleteAppointment(context: Context, index: Int) {
        val prefs = getPrefs(context)
        val appointmentsJson = prefs.getString(KEY_APPOINTMENTS, null)
        val gson = Gson()
        val type = object : TypeToken<MutableList<Appointment>>() {}.type
        val appointments: MutableList<Appointment> =
            if (appointmentsJson != null) gson.fromJson(appointmentsJson, type) else mutableListOf()
        if (index in appointments.indices) {
            appointments.removeAt(index)
            prefs.edit().putString(KEY_APPOINTMENTS, gson.toJson(appointments)).apply()
        }
    }
}
