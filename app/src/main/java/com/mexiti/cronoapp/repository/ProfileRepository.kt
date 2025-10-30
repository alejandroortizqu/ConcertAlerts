package com.mexiti.cronoapp.repository

import com.mexiti.cronoapp.room.ProfileDao
import com.mexiti.cronoapp.room.ProfileEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val profileDao: ProfileDao
) {
    fun observeProfile(): Flow<ProfileEntity?> = profileDao.observe()

    suspend fun saveProfile(profile: ProfileEntity) {
        profileDao.upsert(profile)
    }
}
