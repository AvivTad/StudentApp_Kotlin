package com.example.studentapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import com.example.studentapp.ui.theme.StudentAppTheme
import com.example.studentapp.activity.StudentsListScreen
import com.example.studentapp.repository.StudentsRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current

            StudentAppTheme {
                StudentsListScreen(students = StudentsRepository.getAllStudents(), context = context)
            }
        }
    }
}