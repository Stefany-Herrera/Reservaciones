package com.example.reservacioneshotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var listareservaciones = emptyList<Reservacion>()
        val database = AppDatabase.getDatabase(this)

        database.reservacion().getAll().observe(this, Observer {
            listareservaciones = it

            val adapter = ReservacionAdapter(this, listareservaciones)
            listaR.adapter = adapter
        })
        listaR.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, ReservacionActivity::class.java)
            intent.putExtra("id", listareservaciones[position].idReservacion)
            startActivity(intent)
        }

        floatingActionButton.setOnClickListener {
            val intent = Intent(this, nuevaReservacionActivity::class.java)
            startActivity(intent)
        }
    }

}