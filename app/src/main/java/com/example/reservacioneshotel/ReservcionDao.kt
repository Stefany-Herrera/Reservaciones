package com.example.reservacioneshotel

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ReservcionDao {
    @Query("SELECT * FROM Reservaciones")
    fun getAll(): LiveData<List<Reservacion>>

    @Query("SELECT * FROM Reservaciones WHERE idReservacion = :id")
    fun get(id:Int): LiveData<Reservacion>

    @Insert
    fun insertAll(vararg Reservaciones: Reservacion): List<Long>

    @Update
    fun update(Reservaciones: Reservacion)

    @Delete
    fun delete(Reservaciones: Reservacion)
}