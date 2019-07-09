package com.example.alumnoapp.Interface

import com.example.alumnoapp.Model.Asesoria

interface IFirebaseLoadDone {
    fun onfirebaseLoadSuccess (asesoriaList:List<Asesoria>)
    fun onfirebaseLoadFailed (message: String)
}