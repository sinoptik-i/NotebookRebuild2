package com.example.notebookrebuild2


import android.os.Bundle
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

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


@Inject lateinit var settingsMaster: SettingsMaster

@Inject lateinit var noteRepository : NoteRepository


 private val oneNoteVIewModel: OneNoteVIewModel by viewModels()
    private val allNotesVIewModel: AllNotesVIewModel by viewModels()
 //private val oneNoteVIewModel by lazy {ViewModelProviders.of(this).get(UserViewModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    MainFun(
                        allNotesVIewModel = allNotesVIewModel,
                        oneNoteVIewModel = oneNoteVIewModel,
                        settingsMaster = settingsMaster
                    )
                }
            }
        }
    }
}

@Composable
fun MainFun(
    allNotesVIewModel: AllNotesVIewModel,
    oneNoteVIewModel: OneNoteVIewModel, settingsMaster: SettingsMaster
) {
    // if true - table with all notes, false - one note
    var seeAllNotes by rememberSaveable { mutableStateOf(true) }
    val note = remember { mutableStateOf(Note(0, "", "", "")) }
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

