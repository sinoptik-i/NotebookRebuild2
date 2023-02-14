package com.example.notebookrebuild2.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent
import sin.android.notebook.data.INotesRepository
import sin.android.notebook.data.NoteRepository


// etot module vawe ne nujen
@Module
@InstallIn(SingletonComponent::class)
abstract class NoteRepositoryModule2 {
    @Binds
    abstract fun providesNoteRepository(noteRepository: NoteRepository): INotesRepository

}
