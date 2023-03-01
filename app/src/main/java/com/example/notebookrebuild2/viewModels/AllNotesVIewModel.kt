package sin.android.notebook.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import sin.android.notebook.data.INotesRepository
import sin.android.notebook.data.Note
import javax.inject.Inject

@HiltViewModel
class AllNotesVIewModel @Inject constructor(
    private val noteRepository: INotesRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")

    lateinit var  TAG:String

    init {
        TAG=this.javaClass.simpleName
        Log.e(TAG,"I born")
    }


    override fun onCleared() {
        Log.e(TAG,"Ama dead")
        super.onCleared()
    }

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