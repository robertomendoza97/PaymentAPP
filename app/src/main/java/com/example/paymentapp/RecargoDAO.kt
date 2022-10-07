package com.example.paymentapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecargoDAO {

    @Query("SELECT * FROM Recargo")
     fun obtenerTodos(): List<Recargo>

    @Query("SELECT * FROM Recargo ORDER BY id DESC LIMIT 1")
     fun obtenerUltimo(): Recargo

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun agregarRecargo(recargo: List<Recargo>)
}