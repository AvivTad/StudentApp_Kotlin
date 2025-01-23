package com.example.studentapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studentapp.model.Student
import com.example.studentapp.repository.StudentsRepository
import com.example.studentapp.ui.theme.StudentAppTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.studentapp.R
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

class StudentsListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentAppTheme {
                StudentsListScreen(students = StudentsRepository.getAllStudents())
            }
        }
    }
}

@Composable
fun StudentsListScreen(students: List<Student>) {
    val studentsState = remember { mutableStateOf(students.toMutableList()) }
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Students List",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp)
                )
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(studentsState.value.size) { index ->
                        StudentItem(student = studentsState.value[index]) { checked ->
                            val updatedStudent = studentsState.value[index].copy(isChecked = checked)
                            StudentsRepository.updateStudent(updatedStudent)
                            val updatedStudents = studentsState.value.toMutableList()
                            updatedStudents[index] = updatedStudent
                            studentsState.value = updatedStudents
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun StudentItem(student: Student, onCheckedChange: (Boolean) -> Unit) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                val intent = Intent(context, StudentDetailsActivity::class.java)
                intent.putExtra("studentId", student.id.toString())
                context.startActivity(intent)
            },
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_student_pic),
            contentDescription = "Student Pic",
            modifier = Modifier.size(50.dp)
        )

        Column {
            Text(text = "Name: ${student.name}")
            Text(text = "ID: ${student.id}")
        }

        Checkbox(
            checked = student.isChecked,
            onCheckedChange = { checked ->
                onCheckedChange(checked)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStudentsListScreen() {
    val students = StudentsRepository.getAllStudents()
    StudentAppTheme {
        StudentsListScreen(students = students)
    }
}