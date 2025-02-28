package com.example.medicitas

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.medicitas.utils.PreferenceHelper

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etCedula = findViewById<EditText>(R.id.etCedula)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnLogin.setOnClickListener {
            val cedula = etCedula.text.toString()
            val password = etPassword.text.toString()

            // Caso administrador
            if (cedula == "admin" && password == "admin") {
                startActivity(Intent(this, AdminActivity::class.java))
                finish()
            } else {
                // Para pacientes, la cédula es usuario y contraseña
                if (cedula == password && PreferenceHelper.isPatientRegistered(this, cedula)) {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("patientCedula", cedula)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Credenciales inválidas o paciente no registrado", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
