package com.example.paymentapp

import android.app.Application
import androidx.room.Room

class RecargoApp: Application() {

    val room = Room.databaseBuilder(this, RecargoDB::class.java, "recargos")
        .build()
}