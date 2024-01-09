package com.theseuntaylor.minipe.di

import com.theseuntaylor.minipe.lib_login.data.repository.LoginRepository
import com.theseuntaylor.minipe.lib_login.data.repository.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginModule {
    @Binds
    @Singleton
    abstract fun bindsLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl,
    ): LoginRepository
}