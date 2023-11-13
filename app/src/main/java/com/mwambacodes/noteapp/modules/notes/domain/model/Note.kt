package com.mwambacodes.noteapp.modules.notes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mwambacodes.noteapp.ui.theme.BabyBlue
import com.mwambacodes.noteapp.ui.theme.LightGreen
import com.mwambacodes.noteapp.ui.theme.RedOrange
import com.mwambacodes.noteapp.ui.theme.RedPink
import com.mwambacodes.noteapp.ui.theme.Violet

@Entity
data class Note (
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String): Exception(message)