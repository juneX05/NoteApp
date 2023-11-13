package com.mwambacodes.noteapp.modules.notes.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}

