package com.theseuntaylor.minipe.di

import com.theseuntaylor.minipe.lib_taps.data.repository.TapsRepository
import com.theseuntaylor.minipe.lib_taps.data.repository.TapsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TapsModule {
    @Binds
    @Singleton
    abstract fun bindsTapsRepository(
        tapsRepositoryImpl: TapsRepositoryImpl,
    ): TapsRepository

}