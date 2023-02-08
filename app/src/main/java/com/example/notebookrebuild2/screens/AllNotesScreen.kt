package sin.android.notebook.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notebookrebuild2.SettingsMaster
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import sin.android.notebook.SupportScreens.SupportSelectedMode
import sin.android.notebook.ViewModels.AllNotesVIewModel
import sin.android.notebook.data.Note


val TAG = "AllNotesScreen"


@Composable
fun MyBottomAppBar(
    allNotesVIewModel: AllNotesVIewModel,
    supportSelectedMode: SupportSelectedMode
) {
    BottomAppBar {
        IconButton(onClick = {
            allNotesVIewModel
                .deleteSelectedNotes(supportSelectedMode.getSelectedNotes())
            supportSelectedMode.offSelectedMode()
        }) {
            Icon(Icons.Filled.Delete, contentDescription = "")
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = {
            supportSelectedMode.offSelectedMode()
            supportSelectedMode.cancelAllSelections()
        }) {
            Icon(Icons.Filled.Close, "")
        }
    }
}

@Composable
fun DriverMenu(
    settingsMaster: SettingsMaster
) {
    val scope = rememberCoroutineScope()
    val state = settingsMaster.isDarkThemeFlow().collectAsState(initial = false)

    Row(
        modifier = Modifier
            // .height(40.dp)
            .fillMaxWidth()
            // .padding(16.dp)
            .border(
                BorderStroke(
                    2.dp,
                    if (state.value) {
                        darkColors().primary
                    } else {
                        lightColors().secondary
                    }
                    //Color.LightGray
                ),
                shape = RoundedCornerShape(8.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        //  val chState = settingsMaster.getTheme2()

        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp)
            // .fillMaxHeight()
            //    .padding(bottom = 8.dp)
            ,
            text = "Dark theme"
        )
        // Spacer(modifier = Modifier.weight(1f))
        Switch(
            modifier = Modifier
                .padding(horizontal = 8.dp)

            //    .padding(bottom = 8.dp)
            ,
            checked = state.value,
            onCheckedChange = {
                scope.launch {
                    settingsMaster.saveTheme(it)
                }
            }
        )
    }
}


@Composable
fun SearchAlertDialog(
    allNotesVIewModel: AllNotesVIewModel,
    searchState: MutableState<SearchState>
) {
    val searchArgTitle = rememberSaveable {
        mutableStateOf(allNotesVIewModel.getQuery())
    }
    AlertDialog(onDismissRequest = {
        //if (searchState.value == SearchState.INPUT) {
        if (allNotesVIewModel.getQuery() == "") {
            searchState.value = SearchState.OFF
        } else {
            searchState.value = SearchState.RESULTS
        }
    },
        buttons = {
            Column(
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                TitleTextField(title = searchArgTitle)
                Button(modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.End),
                    onClick = {
                        allNotesVIewModel.setSearchArgNotes(searchArgTitle.value)
                        searchState.value = SearchState.RESULTS
                    }) {
                    Text(text = "Search")
                }
            }
        }
    )
}


@Composable
fun MyTopAppBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    allNotesVIewModel: AllNotesVIewModel,
    searchState: MutableState<SearchState>
) {
    TopAppBar {
        if (searchState.value == SearchState.RESULTS) {
            IconButton(onClick = {
                scope.launch {
                    searchState.value = SearchState.OFF
                    allNotesVIewModel.setSearchArgNotes("")
                }
            }) {
                Icon(Icons.Filled.Close, "")
            }
            Text(
                modifier = Modifier.weight(1f),
                text = allNotesVIewModel.getQuery()
            )
        } else {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(Icons.Filled.Menu, "")
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        IconButton(onClick = {
            searchState.value = SearchState.INPUT
        }) {
            Icon(Icons.Filled.Search, "")
        }
    }
}

enum class SearchState { OFF, INPUT, RESULTS }

@Composable
fun AllNotesView(
    onNoteSelect: (Note) -> Unit,
    onAddNewNoteClicked: () -> Unit,
    allNotesVIewModel: AllNotesVIewModel,
    settingsMaster: SettingsMaster,
) {

    //var searchState by rememberSaveable { mutableStateOf(false) }
    var searchState = rememberSaveable { mutableStateOf(SearchState.OFF) }

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val notes = allNotesVIewModel.getExampleNotes(4)
    val items by allNotesVIewModel.flowAllNotes().collectAsState(initial = notes)

    val supportSelectedMode by remember { mutableStateOf(SupportSelectedMode(items ?: notes)) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MyTopAppBar(
                scope = scope,
                scaffoldState = scaffoldState,
                allNotesVIewModel = allNotesVIewModel,
                searchState = searchState
            )
        },

        bottomBar = {
            if (supportSelectedMode.selectedMode.value) {
                MyBottomAppBar(allNotesVIewModel, supportSelectedMode)
            }
        },
        drawerContent = {
            // if (supportScaffoldMenu.driverSee.value) {
            DriverMenu(settingsMaster)
            //   }
        },
        content = { padding ->
            Column()
            {
                if (searchState.value == SearchState.INPUT) {
                    SearchAlertDialog(
                        allNotesVIewModel = allNotesVIewModel,
                        searchState
                    )
                }


                Box(
                    Modifier.fillMaxSize(),
                    //  contentAlignment = Alignment.BottomEnd
                    //horizontalAlignment = Alignment.End
                ) {
                    val createNewNote = {
                        onNoteSelect(
                            Note(
                                0,
                                "",
                                "",
                                ""
                            )
                        )// Calendar.getInstance().timeInMillis.toInt()))
                        onAddNewNoteClicked()
                    }


                    val mode by supportSelectedMode.selectedMode
                    //var selectedMode by remember { mutableStateOf(false) }
                    LazyColumn(
                        Modifier
                            .padding(top = 4.dp, bottom = 4.dp)
                        //  .align(Alignment.)
                    ) {
                        items(items ?: notes) {
                            var checkState by supportSelectedMode.getCheckState(it)
//                    var checkState by rememberSaveable { mutableStateOf(false) }

                            /* if (deleteButtonClicked) {
                                 Log.e(TAG, "$it, $checkState")
                                 if (checkState) {
                                     allNotesVIewModel.deleteNote(it)
                                 }
                             }*/

                            val selectNote = {
                                onNoteSelect(it)
                                onAddNewNoteClicked()
                            }
                            val itNote = it

                            NoteView(
                                it,
                                mode,//supportSelectedMode.selectedMode.value,
                                checkState,
                                {
                                    supportSelectedMode.changeSelectofNote(itNote, it)
                                },
                                selectNote,
                                {
                                    supportSelectedMode.changeSelectedMode(it)
                                }
                            )
                        }
                    }

                    IconButton(

                        onClick = createNewNote,
                        // po4emy blet nelza BottomEnd???
                        modifier = Modifier.align(Alignment.BottomEnd)

                    ) {
                        Icon(
                            imageVector = Icons.Filled.AddCircle,
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            /*  tint = if (settingsMaster.isDarkThemeFlow()
                                      .collectAsState(initial = false).value
                              ) {
                                  Color(0xFFAAA939)
                              } else {
                                  Color(0xFF6200EE)
                              }*/
                        )

                    }
                }
            }
        }

    )
}


@Preview
@Composable
fun DriverMenuPreview() {
//    DriverMenu(settingsMaster = SettingsMaster(LocalContext.current))
}

/*
@Preview(showBackground = true)
@Composable
fun AllNotesViewPreview() {
    NotebookTheme {
        AllNotesView({}, {},
            AllNotesVIewModel(Application()),
            SettingsMaster(LocalContext.current)
        )
    }
}
*/

/*@Preview
@Composable
fun SearchAlertDialogPreview() {
    SearchAlertDialog(
        //  allNotesVIewModel
        {}
    )
}*/

/*            Button(

                onClick = {
                    allNotesVIewModel
                        .deleteSelectedNotes(supportSelectedMode.getSelectedNotes())
                }) {
                Text(text = "delete selected")
            }
            Button(onClick = { allNotesVIewModel.add7Notes() }) {
                Text(text = "add 7 notes")
            }*/