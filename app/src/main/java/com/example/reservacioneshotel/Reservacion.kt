package com.example.reservacioneshotel

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity(tableName = "Reservaciones")
class Reservacion (
    val tipo:String,
    val precio:Double,
    val descripcion:String,
    val imagen:Int,
    @PrimaryKey(autoGenerate = true)
    var idReservacion: Int = 0
    ) : Serializable {
}