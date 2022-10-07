package com.example.paymentapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Recargo::class],
    version = 1
)
 abstract class RecargoDB:RoomDatabase() {

     abstract fun recargoDao(): RecargoDAO
}