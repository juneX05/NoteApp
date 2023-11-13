package com.mwambacodes.noteapp.modules.notes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mwambacodes.noteapp.modules.notes.presentation.add_note.AddNoteScreen
import com.mwambacodes.noteapp.modules.notes.presentation.notes.NotesScreen
import com.mwambacodes.noteapp.modules.notes.presentation.util.Screen
import com.mwambacodes.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.NotesScreen.route) {
                        composable(
                            route = Screen.NotesScreen.route
                        ) {
                            NotesScreen(navController = navController)
                        }

                        composable(
                            route = Screen.AddNoteScreen.route +
                                "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(name = "noteId"){
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(name = "noteColor"){
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                            )
                        ) {
                            val color = it.arguments?.getInt("noteColor" ) ?: -1
                            AddNoteScreen(
                                navController = navController,
                                noteColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}