package com.mwambacodes.noteapp.modules.notes.domain.use_case

import com.mwambacodes.noteapp.modules.notes.domain.model.InvalidNoteException
import com.mwambacodes.noteapp.modules.notes.domain.model.Note
import com.mwambacodes.noteapp.modules.notes.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("The title of the note cannot be blank")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("The content of the note cannot be blank")
        }

        repository.insertNote(note)
    }

}