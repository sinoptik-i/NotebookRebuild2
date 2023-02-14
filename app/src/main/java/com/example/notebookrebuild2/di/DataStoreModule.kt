package com.example.notebookrebuild2.di

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {
    @Provides
    fun bindDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.createDataStore("notebook")
}