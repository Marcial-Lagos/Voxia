package com.example.guia8.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PerfilEntity::class],
    version = 1,
    exportSchema = false
)
abstract class VoxiaDatabase : RoomDatabase() {

    abstract fun perfilDao(): PerfilDao

    companion object {
        @Volatile
        private var INSTANCIA: VoxiaDatabase? = null

        fun obtenerInstancia(context: Context): VoxiaDatabase {
            return INSTANCIA ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    VoxiaDatabase::class.java,
                    "voxia_database"
                ).build()

                INSTANCIA = instancia
                instancia
            }
        }
    }
}
