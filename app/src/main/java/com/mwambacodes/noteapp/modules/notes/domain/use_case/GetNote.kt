package com.mwambacodes.noteapp.modules.notes.domain.use_case

import com.mwambacodes.noteapp.modules.notes.domain.model.Note
import com.mwambacodes.noteapp.modules.notes.domain.repository.NoteRepository

class GetNote(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }

}