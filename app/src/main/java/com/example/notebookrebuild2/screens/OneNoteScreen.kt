package sin.android.notebook.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import sin.android.notebook.ViewModels.OneNoteVIewModel
import sin.android.notebook.data.Note

@Composable
fun TitleTextField(title: MutableState<String>) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        value = title.value,
        onValueChange = { title.value = it },
        label = { Text("Title:") }
    )
}

@Composable
fun DescriptionTextField(description: MutableState<String>) {
    TextField(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxSize(),
        value = description.value,
        onValueChange = {
            description.value = it
            // onContinueClicked(text)
        },
        label = { Text("Description:") }
    )
}


@Composable
fun OneFullNote(
    note: Note,
    onContinueClicked: () -> Unit,
    oneNoteVIewModel: OneNoteVIewModel
) {
    var textTitle = remember {
        mutableStateOf(
            note.title
        )
    }
//    var textDescription by rememberSaveable { mutableStateOf("") }
    var textDescription = remember {
        mutableStateOf(
            note.description
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val uniteContinuations: () -> Unit = {
            oneNoteVIewModel.tryToAddNote(note.id, textTitle.value, textDescription.value)
            onContinueClicked()
        }
        IconButton(
            onClick = uniteContinuations,
            modifier = Modifier
                .size(60.dp)
                .border(2.dp, MaterialTheme.colors.error, CircleShape)
            /*   .combinedClickable (
                   onClick = uniteContinuations,
                   onLongClick = uniteContinuations
                       ),*/


        ) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = null
            )
        }
      //  Text(text = "${note.time}")
        TitleTextField(title = textTitle)
        DescriptionTextField(description = textDescription)
    }
}


/*
@Preview
@Composable
fun OneFullNotePreview() {
    OneFullNote(

       // note = Note(1, "anyTitle", "ololo pwpw"),
        2,
        {})
}
*/