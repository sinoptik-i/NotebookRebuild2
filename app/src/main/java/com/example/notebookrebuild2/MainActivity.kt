package com.example.notebookrebuild2


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e(TAG, "onCr")
        setContent {
            val darkThemeState = settingsMaster.isDarkThemeFlow().collectAsState(initial = false)

            NotebookRebuild2Theme{
//darkThemeState
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

                    )
                }
            }
        }
    }


}

@Composable
fun MainFun(
    allNotesVIewModel: AllNotesVIewModel,
    oneNoteVIewModel: OneNoteVIewModel,
    settingsMaster: SettingsMaster,

) {

    var seeAllNotes by rememberSaveable { mutableStateOf(true) }

    var note=remember{mutableStateOf(oneNoteVIewModel.currentNotee)}
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

