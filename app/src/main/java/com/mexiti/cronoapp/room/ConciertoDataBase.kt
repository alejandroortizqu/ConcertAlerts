package com.mexiti.cronoapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mexiti.cronoapp.model.ConciertoItem
import javax.xml.namespace.NamespaceContext


@Database(entities = [ConciertoItem::class, ProfileEntity::class], version = 2, exportSchema = false)
abstract class ConciertoDataBase:RoomDatabase() {
    abstract fun conciertoDao(): ConciertoDatabaseDao
    companion object{
        @Volatile
        private var Instance: ConciertoDataBase? = null

        fun getDatabase(context: android.content.Context): ConciertoDataBase{
            return Instance ?: synchronized(this){
                androidx.room.Room.databaseBuilder(
                    context,
                    ConciertoDataBase::class.java,
                    "concierto_db"
                ).build().also { Instance = it }
            }
        }
    }
    abstract fun profileDao(): ProfileDao
}