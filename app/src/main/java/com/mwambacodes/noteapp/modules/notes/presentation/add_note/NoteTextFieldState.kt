package com.mwambacodes.noteapp.modules.notes.presentation.add_note

data class NoteTextFieldState (
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)