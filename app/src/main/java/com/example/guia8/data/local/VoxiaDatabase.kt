package com.example.guia8.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [PerfilEntity::class],
    version = 2,
    exportSchema = false
)
abstract class VoxiaDatabase : RoomDatabase() {

    abstract fun perfilDao(): PerfilDao

    companion object {
        @Volatile
        private var INSTANCIA: VoxiaDatabase? = null

        private val MIGRACION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    ALTER TABLE perfiles
                    ADD COLUMN mejorPuntaje INTEGER NOT NULL DEFAULT 0
                    """.trimIndent()
                )
            }
        }

        fun obtenerInstancia(context: Context): VoxiaDatabase {
            return INSTANCIA ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    VoxiaDatabase::class.java,
                    "voxia_database"
                )
                    .addMigrations(MIGRACION_1_2)
                    .build()

                INSTANCIA = instancia
                instancia
            }
        }
    }
}
