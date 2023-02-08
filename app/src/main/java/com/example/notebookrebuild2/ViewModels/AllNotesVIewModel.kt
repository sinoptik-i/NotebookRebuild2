package sin.android.notebook.ViewModels

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import sin.android.notebook.data.Note
import sin.android.notebook.data.NoteDao
import sin.android.notebook.data.NoteDatabase
import sin.android.notebook.data.NoteRepository

class AllNotesVIewModel(// @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _query = MutableStateFlow<String>("")

    fun flowAllNotes() = _query
        // .debounce(1000L)
        // .distinctUntilChanged()
        .combine(noteRepository.allNotes()) { query, items ->
            if (query.isEmpty()) {
                items
            } else {
                items.filter { it.title.contains(query) }
            }

        }.distinctUntilChanged()


    fun setSearchArgNotes(searchArgTitle: String) {
        _query.value = searchArgTitle
    }

    fun getQuery() = _query.value

    fun deleteSelectedNotes(notes: List<Note>) {
        for (note in notes) {
            noteRepository.deleteNote(note)
        }
    }

    // val supportSelectedMode=SupportSelectedMode()
    fun add7Notes() {
        for (i in 1..7) {
            noteRepository.addNote(Note(0, "${i}", "${i}", ""))
        }
    }

    fun getExampleNotes(count: Int) = List(count) {
        Note(
            it,
            "$it title",
            "descr",
            ""
        )
    }
}