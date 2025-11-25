package com.mexiti.cronoapp.repository

import com.mexiti.cronoapp.model.ConciertoItem
import com.mexiti.cronoapp.room.ConciertoDatabaseDao
import com.mexiti.cronoapp.room.ProfileDao
import com.mexiti.cronoapp.room.ProfileEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ConciertoRepository@Inject constructor(private val dao: ConciertoDatabaseDao, private val profileDao: ProfileDao) {

    fun getAllItems(): Flow<List<ConciertoItem>> = dao.getItems()

    suspend fun insertItem(item: ConciertoItem) {
        dao.insert(item)
    }

    suspend fun deleteItem(item: ConciertoItem) {
        dao.delete(item)
    }


    fun observeProfile(): Flow<ProfileEntity?> = profileDao.observe()
    suspend fun saveProfile(p: ProfileEntity) = profileDao.upsert(p)

}