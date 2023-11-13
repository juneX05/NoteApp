package com.mwambacodes.noteapp.modules.notes.presentation.add_note

import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mwambacodes.noteapp.modules.notes.domain.model.Note
import com.mwambacodes.noteapp.modules.notes.presentation.add_note.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: AddNoteViewModel = hiltViewModel()
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }
    
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddNoteViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState
                        .showSnackbar(
                            message = event.message,
                            actionLabel = "Dismiss",
                            // Defaults to SnackbarDuration.Short
                            duration = SnackbarDuration.Short
                        )
                }
                is AddNoteViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddNoteEvent.SaveNote)
                },
                modifier = Modifier.clip(CircleShape).background(MaterialTheme.colorScheme.primary),
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save note")
            }
        }
    ) {
        Surface(
            color = Color(noteBackgroundAnimatable.value.toArgb())
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Note.noteColors.forEach{color ->
                        val colorInt = color.toArgb()
                        Box(modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.noteColor.value == colorInt) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddNoteEvent.ChangeColor(colorInt))
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TransparentHintTextField(
                    text = titleState.text,
                    hint = titleState.hint,
                    onValueChange = {
                        viewModel.onEvent(AddNoteEvent.EnteredTitle(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddNoteEvent.ChangeTitleFocus(it))
                    },
                    isHintVisible = titleState.isHintVisible,
                    textStyle = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(16.dp))

                TransparentHintTextField(
                    text = contentState.text,
                    hint = contentState.hint,
                    onValueChange = {
                        viewModel.onEvent(AddNoteEvent.EnteredContent(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddNoteEvent.ChangeContentFocus(it))
                    },
                    isHintVisible = contentState.isHintVisible,
                    textStyle = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }

    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(Alignment.Bottom),
    )
}