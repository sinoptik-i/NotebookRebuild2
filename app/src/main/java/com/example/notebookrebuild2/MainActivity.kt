package com.example.notebookrebuild2


import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.notebookrebuild2.ui.theme.NotebookRebuild2Theme
import dagger.hilt.android.AndroidEntryPoint
import sin.android.notebook.ViewModels.AllNotesVIewModel
import sin.android.notebook.ViewModels.OneNoteVIewModel
import sin.android.notebook.data.Note
import sin.android.notebook.data.NoteRepository
import sin.android.notebook.screens.AllNotesView
import sin.android.notebook.screens.OneFullNote
import javax.inject.Inject


const val ID = "NOTE_ID"
const val TITLE = "NOTE_TITLE"
const val DESCRIPTION = "NOTE_DESCRIPTION"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    //val TAG =this.localClassName - 4e za nah
    private val TAG = "MainActivity"

    @Inject
    lateinit var settingsMaster: SettingsMaster

    @Inject
    lateinit var noteRepository: NoteRepository


    private val oneNoteVIewModel: OneNoteVIewModel by viewModels()
    private val allNotesVIewModel: AllNotesVIewModel by viewModels()
    private var startNote = Note(0, "", "", "")

    //private val oneNoteVIewModel by lazy {ViewModelProviders.of(this).get(UserViewModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e(TAG, "onCr")

        /*   if(resources.configuration.orientation==Configuration.ORIENTATION_PORTRAIT){

           }*/
        // val context=applicationContext

        /*  val noteRepository = NoteRepository(
             NoteDatabase.getDatabase(context).noteDao(),
             CoroutineScope(Dispatchers.IO + SupervisorJob())
         )*/

        //val factory=ViewModelProvider.Factory

        // val allNotesVIewModel = AllNotesVIewModel(noteRepository)
        // val oneNoteVIewModel = OneNoteVIewModel(noteRepository)
        //val settingsMaster = SettingsMaster(context.createDataStore("settings"))


        setContent {
            NotebookRebuild2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Log.e(TAG, "beforeMainFun")
                    MainFun(
                        allNotesVIewModel = allNotesVIewModel,
                        oneNoteVIewModel = oneNoteVIewModel,
                        settingsMaster = settingsMaster,
                        startNote
                    )
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(ID, oneNoteVIewModel.currentId)
        outState.putString(TITLE, oneNoteVIewModel.currentTitle)
        outState.putString(DESCRIPTION, oneNoteVIewModel.currentDescription)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val currentId = savedInstanceState.getInt(ID, 0)
        val currentTitle = savedInstanceState.getString(TITLE, "")
        val currentDescription = savedInstanceState.getString(DESCRIPTION, "")
        if (currentDescription != "" || currentTitle != "") {
            startNote = Note(
                currentId,
                currentTitle,
                currentDescription,
                oneNoteVIewModel.getNowTime()
            )
        }

    }


}

@Composable
fun MainFun(
    allNotesVIewModel: AllNotesVIewModel,
    oneNoteVIewModel: OneNoteVIewModel,
    settingsMaster: SettingsMaster,
    startNote: Note
) {
    // if true - table with all notes, false - one note
    var seeAllNotes by rememberSaveable { mutableStateOf(true) }
    // val note = remember { mutableStateOf(Note(0, "", "", "")) }
    val note = remember { mutableStateOf(startNote) }
    if (seeAllNotes) {
        AllNotesView(
            onNoteSelect = {
                note.value = it
            },
            onAddNewNoteClicked = { seeAllNotes = false },
            allNotesVIewModel = allNotesVIewModel, settingsMaster
        )
    } else {
        OneFullNote(
            note.value,
            oneNoteVIewModel = oneNoteVIewModel,
            onContinueClicked = { seeAllNotes = true })
    }
}

