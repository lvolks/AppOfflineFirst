package dev.volks.applocadora.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//BANCO DE DADOS SQLITE JUNTO DE ROOM
@Database(entities = [Filme::class], version = 1)
abstract class SQLite : RoomDatabase() {

    abstract fun filmeDao() : FilmeDao

    companion object {

        @Volatile
        private var INSTANCE: SQLite? = null

        fun get(context: Context): SQLite {
            if (INSTANCE == null) {

                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        SQLite::class.java,
                        "BancoSQLite.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }

    }
}
