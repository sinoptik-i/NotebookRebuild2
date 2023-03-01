package sin.android.notebook.SupportScreens


import androidx.compose.runtime.*
import sin.android.notebook.data.Note

class SupportSelectedMode(notes: List<Note>) {

    var selectedMode = mutableStateOf(false)

    val mapNotes = notes.associateWith { mutableStateOf(false) }.toMutableMap()


    fun getCheckState(note: Note) = mapNotes.getOrPut(note) {
        mutableStateOf(false)
    }

    fun changeSelectofNote(note: Note, isSelected: Boolean) {
        getCheckState(note).value = isSelected
    }

    fun changeSelectedMode(note: Note) {
        selectedMode.value = !selectedMode.value
        if (selectedMode.value) {
            changeSelectofNote(note, true)
        } else {
            mapNotes.clear()
        }
    }

    fun offSelectedMode() {
        selectedMode.value = false
    }

    fun cancelAllSelections(){
        for(mapNote in mapNotes){
            changeSelectofNote(mapNote.key,false)
        }
    }

    fun getSelectedNotes(): List<Note> {
        return mapNotes.filter { (key, value) ->
            value.value == true
        }.keys.toList()

    }
}