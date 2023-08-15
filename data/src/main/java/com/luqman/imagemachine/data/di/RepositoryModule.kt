package com.luqman.imagemachine.data.di

import android.content.Context
import androidx.room.Room
import com.luqman.imagemachine.data.repository.DataRepository
import com.luqman.imagemachine.data.repository.DataSource
import com.luqman.imagemachine.database.MachineDatabase
import com.luqman.imagemachine.database.dao.MachineDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MachineDatabase = Room.databaseBuilder(
        context = context,
        klass = MachineDatabase::class.java,
        name = "machine_database"
    ).build()

    @Provides
    fun provideMachineDao(database: MachineDatabase): MachineDao = database.machineDao()

    @Provides
    fun provideRepository(
        dao: MachineDao
    ): DataSource = DataRepository(dao, Dispatchers.IO)
}