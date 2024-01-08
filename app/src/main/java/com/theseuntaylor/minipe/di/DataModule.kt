package com.theseuntaylor.minipe.di

import android.content.Context
import androidx.room.Room
import com.theseuntaylor.minipe.R
import com.theseuntaylor.minipe.lib_taps.local.TapsDatabase
import com.theseuntaylor.minipe.lib_taps.local.TapsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): TapsDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            TapsDatabase::class.java,
            context.getString(R.string.taps_db)
        ).build()
    }

    @Provides
    fun provideTaskDao(database: TapsDatabase): TapsDao = database.tapsDao()
}