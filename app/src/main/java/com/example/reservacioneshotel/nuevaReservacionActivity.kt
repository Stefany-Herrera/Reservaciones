package com.example.reservacioneshotel

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_nueva_reservacion.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class nuevaReservacionActivity : AppCompatActivity() {
    private val SELECT_ACTIIVTY = 56
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_reservacion)

        var idReservacion: Int? = null

        if (intent.hasExtra("reservacion")){
            val reservacion = intent.extras?.getSerializable("reservacion") as Reservacion

            tipo_et.setText(reservacion.tipo)
            precio_et.setText(reservacion.precio.toString())
            descripcion_et.setText(reservacion.descripcion)
            idReservacion = reservacion.idReservacion
            val imageUri = ImagenController.getImageUri(this,idReservacion.toLong())
            imageSelect_iv.setImageURI(imageUri)
        }

        val database = AppDatabase.getDatabase(this)

        save_btn.setOnClickListener{
            val tipo = tipo_et.text.toString()
            val precio = precio_et.text.toString().toDouble()
            val descripcion = descripcion_et.text.toString()

            val reservacion = Reservacion(tipo, precio, descripcion, R.drawable.ic_launcher_background)

            if (idReservacion != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    reservacion.idReservacion = idReservacion

                    database.reservacion().update(reservacion)
                    imageUri?.let {
                        val intent = Intent()
                        intent.data = it
                        setResult(Activity.RESULT_OK, intent)
                        ImagenController.saveImage(this@nuevaReservacionActivity, idReservacion.toLong(), it)
                    }

                    this@nuevaReservacionActivity.finish()
                }
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                   val id = database.reservacion().insertAll(reservacion)[0]

                    imageUri?.let {
                        ImagenController.saveImage(this@nuevaReservacionActivity, id, it)
                    }

                    this@nuevaReservacionActivity.finish()
                }
            }
        }
        imageSelect_iv.setOnClickListener {
            ImagenController.selectPhotoFromGallery(this,SELECT_ACTIIVTY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when{
            requestCode == SELECT_ACTIIVTY && resultCode == Activity.RESULT_OK -> {
                imageUri = data!!.data
                imageSelect_iv.setImageURI(imageUri)
            }
        }
    }
}
