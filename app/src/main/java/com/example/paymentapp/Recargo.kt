package com.example.paymentapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recargo(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val monto: String,
    val tipoTarjeta: String,
    val banco: String,
    val cuotas: String,
    val total: String
)
