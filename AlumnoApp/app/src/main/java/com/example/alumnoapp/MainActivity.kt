package com.example.alumnoapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.alumnoapp.Interface.IFirebaseLoadDone
import com.example.alumnoapp.Model.Asesoria
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IFirebaseLoadDone {
    override fun onfirebaseLoadSuccess(asesoriaList: List<Asesoria>) {
        //get properties asesoria
        val asesoria_dia = getAsesoriaDiaList(asesoriaList)
        // creación del adapter
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,asesoria_dia)
        searchable_Profesor.adapter = adapter
    }

    private fun getAsesoriaDiaList(asesoriaList: List<Asesoria>): List<String> {
        val result = ArrayList<String>()
        for(asesoria  in asesoriaList)
            result.add(asesoria.dia!!)
        return result
    }

    override fun onfirebaseLoadFailed(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    lateinit var ref : DatabaseReference
    lateinit var iFirebaseLoadDone: IFirebaseLoadDone

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //inicializar interface
        iFirebaseLoadDone = this

        //instance DB
        ref = FirebaseDatabase.getInstance().getReference("asesorias")

        //carga de datos
        ref.addValueEventListener(object:ValueEventListener{
            var asesoriaList : MutableList<Asesoria> = ArrayList<Asesoria>()
            override fun onCancelled(p0: DatabaseError) {
                iFirebaseLoadDone.onfirebaseLoadFailed(p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                for(asesoriaSnapShot in p0.children)
                    asesoriaList.add(asesoriaSnapShot.getValue<Asesoria>(Asesoria::class.java!!)!!)
                iFirebaseLoadDone.onfirebaseLoadSuccess(asesoriaList)
            }

        })
    }
}
