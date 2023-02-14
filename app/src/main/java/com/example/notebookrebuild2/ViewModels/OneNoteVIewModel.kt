package sin.android.notebook.ViewModels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import sin.android.notebook.data.INotesRepository
import sin.android.notebook.data.Note
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class OneNoteVIewModel @Inject constructor(
    private val noteRepository: INotesRepository
) : ViewModel() {

    private fun addNote(note: Note) = noteRepository.addNote(note)

    fun tryToAddNote(id: Int, title: String, description: String): Boolean {
        if (title != "") {
            addNote(
                Note(
                    id, title, description,
                    getNowTime()
                )
            )
            return true
        } else if (description == "") {
            return false
        } else {
            val titleLength = if (description.length > 10) {
                10
            } else {
                description.length
            }
            addNote(
                Note(
                    id, description.substring(0, titleLength), description,
                    getNowTime()
                )
            )
            return true
        }


    }

    fun getNowTime(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy hh:mm")
        return dateFormat.format(Calendar.getInstance().timeInMillis)
    }
}