package com.adhi.komik.di

import com.adhi.komik.data.KomikRepository
import com.adhi.komik.data.KomikRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideKomikRepository(komikRepositoryImpl: KomikRepositoryImpl): KomikRepository
}