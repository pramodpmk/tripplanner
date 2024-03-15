package com.example.composeapp.di

import android.content.Context
import com.example.composeapp.ComposeApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApplication(@ApplicationContext app: Context): ComposeApp {
        return app as ComposeApp
    }

    @Provides
    fun provideContext(@ApplicationContext app: Context): Context {
        return app
    }
}
