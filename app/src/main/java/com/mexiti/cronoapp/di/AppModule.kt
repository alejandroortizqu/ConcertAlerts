package com.mexiti.cronoapp.di

import android.content.Context
import com.mexiti.cronoapp.repository.ConciertoRepository
import com.mexiti.cronoapp.room.ConciertoDataBase
import com.mexiti.cronoapp.room.ConciertoDatabaseDao
import com.mexiti.cronoapp.room.ProfileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{
    /*@Singleton
    @Provides
    fun providesCronosDao(cronoDataBase: ConciertoDataBase):ConciertoDatabaseDao{
        return database.conciertoD
    }

    @Singleton
    @Provides
    fun providesCronosDatabase(@ApplicationContext context: Context):ConciertoDataBase{
        return Room.databaseBuilder(
            context= context,
            ConciertoDataBase::class.java,
            name = "cronos_db"
        ).fallbackToDestructiveMigration()
            .build()
    }*/

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ConciertoDataBase {
        return ConciertoDataBase.getDatabase(context)
    }

    // 2. Proporciona el DAO
    @Provides
    fun provideConciertoDao(database: ConciertoDataBase): ConciertoDatabaseDao {
        return database.conciertoDao()
    }

    // 3. Proporciona el Repositorio
    @Provides
    fun provideConciertoRepository(conciertoDao: ConciertoDatabaseDao, profileDao: ProfileDao): ConciertoRepository {
        return ConciertoRepository(conciertoDao, profileDao)
    }

    @Provides
    @Singleton
    fun providesProfileDao(db: ConciertoDataBase): ProfileDao = db.profileDao()

}



/*package com.mexiti.cronoapp.di

import android.content.Context
import androidx.room.Room
import com.mexiti.cronoapp.repository.ProfileRepository
import com.mexiti.cronoapp.room.AppDatabase
import com.mexiti.cronoapp.room.ProfileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            AppDatabase::class.java,
            name = "app_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideProfileDao(db: AppDatabase): ProfileDao = db.profileDao()

    @Singleton
    @Provides
    fun provideProfileRepository(profileDao: ProfileDao): ProfileRepository {
        return ProfileRepository(profileDao)
    }

}*/
