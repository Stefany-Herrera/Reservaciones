package com.example.reservacioneshotel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.item_reservacion.view.*

class ReservacionAdapter(private val mContext: Context, private val listaReservacion: List<Reservacion>) : ArrayAdapter<Reservacion>(mContext, 0, listaReservacion) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.item_reservacion, parent, false)

        val reservacion = listaReservacion[position]

        layout.tipo.text = reservacion.tipo
        layout.precio.text = "$${reservacion.precio}"
        val imageUri = ImagenController.getImageUri(mContext, reservacion.idReservacion.toLong())
        layout.imageView.setImageURI(imageUri)

        return layout
    }
}