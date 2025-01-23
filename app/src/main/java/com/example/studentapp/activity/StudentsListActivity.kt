package com.example.studentapp.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.studentapp.model.Student
import com.example.studentapp.ui.theme.StudentAppTheme
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import com.example.studentapp.StudentAdapter


class StudentsListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentAppTheme {
                StudentsListScreen(students = getSampleStudents())
            }
        }
    }
}

@Composable
fun StudentsListScreen(students: List<Student>) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "students list",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(students.size) { index ->
                StudentItem(student = students[index])
            }
        }
    }
}

@Composable
fun StudentItem(student: Student) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Filled.Person, contentDescription = "Student Icon")

        Column {
            Text(text = "Name: ${student.name}")
            Text(text = "ID: ${student.id}")
        }
    }
}

fun getSampleStudents(): List<Student> {
    return listOf(
        Student(id = 1, name = "Aviv Tadmor"),
        Student(id = 2, name = "Gil Salton")
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewStudentsListScreen() {
    StudentAppTheme {
        StudentsListScreen(students = getSampleStudents())
    }
}