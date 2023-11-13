package com.mwambacodes.noteapp.modules.notes.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mwambacodes.noteapp.modules.notes.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }

}