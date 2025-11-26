package com.mexiti.cronoapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mexiti.cronoapp.model.ConciertoItem

@Database(

    entities = [ProfileEntity::class, ConciertoItem::class],
    version = 1,
    exportSchema = false
)
abstract class ConciertoDataBase : RoomDatabase() {

    abstract fun conciertoDao(): ConciertoDatabaseDao
    abstract fun profileDao(): ProfileDao // Esta función debe ir aquí

    companion object{
        @Volatile
        private var Instance: ConciertoDataBase? = null

        fun getDatabase(context: android.content.Context): ConciertoDataBase{
            return Instance ?: synchronized(this){
                androidx.room.Room.databaseBuilder(
                    context,
                    ConciertoDataBase::class.java,
                    "concierto_db"
                )
                    // 🟢 AÑADIR ESTO: Necesario para reconstruir la DB con la nueva tabla
                    .fallbackToDestructiveMigration()
                    .build().also { Instance = it }
            }
        }
    }
}
