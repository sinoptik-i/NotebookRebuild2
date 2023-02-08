package sin.android.notebook.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/*interface INotesRepository {
    fun allNotes(): Flow<List<Note>>
}*/

//@Singleton
class NoteRepository(// @Inject constructor(
    private val noteDao: NoteDao,
    private val scope: CoroutineScope
)  {

  //  var cache: List<Note> = emptyList()
    /*private val noteDao: NoteDao
        get() = NoteDatabase.getDatabase(getApplication()).noteDao()*/

    /*val allNotes: Flow<List<Note>> = noteDao.getAllNotes()
        .onEach { cache = it }
        .onStart { emit(cache) }*/

    fun allNotes() =  noteDao.getAllNotes()

    fun addNote(note: Note) {
        scope.launch {
            noteDao.addNote(note)
        }
    }

   // fun getNote(noteID: Int) = noteDao.getNoteById(noteID)

/*
    fun getNote(noteID: Int) {
        scope.launch {
            noteDao.getNoteById(noteID).collect {  }
        }
    }
*/

    fun deleteNote(note: Note) {
        scope.launch {
            noteDao.deleteNote(note)
        }
    }
}