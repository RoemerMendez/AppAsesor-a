package com.example.alumnoapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var txtUser : EditText
    private lateinit var txtPassword : EditText
    private lateinit var progressBar : ProgressBar
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //ref de los datos obtenidos
        txtUser = findViewById(R.id.txtUser)
        txtPassword = findViewById(R.id.txtPassword)
        progressBar = findViewById(R.id.progressBar)
        //instancia de la DB
        auth = FirebaseAuth.getInstance()
    }

    fun iniciarSesion(view: View){
        loginUser()
    }

    private fun loginUser(){

        //obtencion de datos
        val user : String = txtUser.text.toString()
        val password : String = txtPassword.text.toString()
        //validación
        if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)){
            progressBar.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(user,password)
                .addOnCompleteListener(this){
                    task ->
                    if (task.isSuccessful){
                        action()
                    }else{
                        Toast.makeText(this, "Usuario o contraseña incorrectos",Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun action(){
        startActivity(Intent(this,MainActivity::class.java))
    }
}