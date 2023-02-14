package com.example.notebookrebuild2.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import sin.android.notebook.data.INotesRepository
import sin.android.notebook.data.NoteDatabase
import sin.android.notebook.data.NoteRepository

@Module
@InstallIn(SingletonComponent::class)
object NoteRepositoryModule1 {

    @Provides
    fun provideNoteDao(
        @ApplicationContext context: Context
    ) =
        NoteDatabase.getDatabase(context).noteDao()

    @Provides
    fun provideCoroutineScope() =
        CoroutineScope(Dispatchers.IO + SupervisorJob())


}