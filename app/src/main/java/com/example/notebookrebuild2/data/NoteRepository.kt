package sin.android.notebook.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


interface INotesRepository {
    fun allNotes(): Flow<List<Note>>

    fun addNote(note: Note)

    fun deleteNote(note: Note)
}

@Singleton
class NoteRepository @Inject constructor(
    private val noteDao: NoteDao,
    private val scope: CoroutineScope
) :INotesRepository {
    override fun allNotes() =  noteDao.getAllNotes()

    override  fun addNote(note: Note) {
        scope.launch {
            noteDao.addNote(note)
        }
    }

    override  fun deleteNote(note: Note) {
        scope.launch {
            noteDao.deleteNote(note)
        }
    }
}

//  var cache: List<Note> = emptyList()
/*private val noteDao: NoteDao
    get() = NoteDatabase.getDatabase(getApplication()).noteDao()*/

/*val allNotes: Flow<List<Note>> = noteDao.getAllNotes()
    .onEach { cache = it }
    .onStart { emit(cache) }*/

// fun getNote(noteID: Int) = noteDao.getNoteById(noteID)

/*
    fun getNote(noteID: Int) {
        scope.launch {
            noteDao.getNoteById(noteID).collect {  }
        }
    }
*/