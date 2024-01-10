package com.theseuntaylor.minipe.di

import com.theseuntaylor.minipe.core.data.DataStoreService
import com.theseuntaylor.minipe.core.data.DataStoreServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {
    @Singleton
    @Binds
    abstract fun bindsDataStore(
        dataStoreImpl: DataStoreServiceImpl,
    ): DataStoreService
}