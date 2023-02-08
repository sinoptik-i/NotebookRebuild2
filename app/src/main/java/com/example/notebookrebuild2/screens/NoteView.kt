package sin.android.notebook.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sin.android.notebook.data.Note
import java.text.SimpleDateFormat


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteView(
    note: Note,
    selectedMode: Boolean,
    checkState: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    //click to add or change Note
    onClicked: () -> Unit,
    //change selectable mode
    onLongClicked: () -> Unit
) {

    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .height(80.dp)
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .combinedClickable(
                onClick = {
                    if (selectedMode) {
                        onLongClicked()
                    } else {
                        onClicked()
                    }
                },
                onLongClick = {
                    onLongClicked()
                    //    openDialog.value = true
                }
            )
    ) {
        Row(
            Modifier
                .padding(24.dp)
                .fillMaxSize()
                //.fillMaxWidth()
        ) {
            Text(
                text = note.title,
                modifier = Modifier
                    .weight(3f)
            )
            if (selectedMode) {

                Checkbox(
                    modifier = Modifier
                        .fillMaxHeight(),
                    //    .background(Color.White),
                    colors=CheckboxDefaults.colors(uncheckedColor = Color.White),
                    checked = checkState,
                    onCheckedChange = {
                        onCheckedChange(it)
                    }
                )
            } else {
                Text(
                    text = note.time,
                    modifier = Modifier
                        .weight(2f)
                )
            }
        }
    }

}

@Preview
@Composable
fun NoteViewPreview() {
    NoteView(
        note = Note(
            0, "title",
            "descr",
            ""
        ),
        selectedMode = true,
        checkState = true,
        onCheckedChange = {},
        onClicked = {  }) {
    }
}
