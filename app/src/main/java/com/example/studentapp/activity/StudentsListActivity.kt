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

@Composable
fun StudentsListScreen(students: List<Student>, context: Context) {
    val studentsState = remember { mutableStateOf(students.toMutableList()) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Students List",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(studentsState.value.size) { index ->
                    StudentItem(
                        student = studentsState.value[index],
                        onCheckedChange = { checked ->
                            val updatedStudent = studentsState.value[index].copy(isChecked = checked)
                            StudentsRepository.updateStudent(updatedStudent)

                            val updatedStudents = studentsState.value.toMutableList()
                            updatedStudents[index] = updatedStudent
                            studentsState.value = updatedStudents
                        },
                        context = context
                    )
                }
            }
        }
    }
}


@Composable
fun StudentItem(student: Student, onCheckedChange: (Boolean) -> Unit, context: Context) {
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
    StudentAppTheme {
        StudentsListScreen(students = StudentsRepository.getAllStudents(), context = LocalContext.current)
    }
}