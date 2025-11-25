package com.mexiti.cronoapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mexiti.cronoapp.model.ConciertoItem
import kotlinx.coroutines.flow.Flow


@Dao  //Data Access Observer
interface ConciertoDatabaseDao {
    //Crud
    @Query("SELECT * FROM items_concierto")
    fun getItems(): Flow<List<ConciertoItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ConciertoItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: ConciertoItem)

    @Delete
    suspend fun delete(item: ConciertoItem)

}