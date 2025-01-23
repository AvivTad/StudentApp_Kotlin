package com.example.studentapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import com.example.studentapp.model.Student
import com.example.studentapp.repository.StudentsRepository
import com.example.studentapp.ui.theme.StudentAppTheme
import com.example.studentapp.MainActivity


class EditStudentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val studentId = intent.getIntExtra("studentId", -1)
        val student = StudentsRepository.getStudentById(studentId)

        setContent {
            StudentAppTheme {
                if (student != null) {
                    EditStudentScreen(student = student)
                } else {
                    Text(text = "Student not found", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@Composable
fun EditStudentScreen(student: Student) {
    var name by remember { mutableStateOf(TextFieldValue(student.name)) }
    var phone by remember { mutableStateOf(TextFieldValue(student.phone)) }
    var address by remember { mutableStateOf(TextFieldValue(student.address)) }

    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Edit Student Details", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Name:")
        TextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Name") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Phone:")
        TextField(
            value = phone,
            onValueChange = { phone = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Phone") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Address:")
        TextField(
            value = address,
            onValueChange = { address = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Address") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    val intent = Intent(context, StudentDetailsActivity::class.java)
                    intent.putExtra("studentId", student.id)
                    context.startActivity(intent)
                    (context as? ComponentActivity)?.finish()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Cancel")
            }

            Button(
                onClick = {
                    val updatedStudent = student.copy(
                        name = name.text,
                        phone = phone.text,
                        address = address.text
                    )
                    StudentsRepository.updateStudent(updatedStudent)
                    Toast.makeText(context, "Student updated successfully", Toast.LENGTH_SHORT).show()

                    val intent = Intent(context, StudentDetailsActivity::class.java)
                    intent.putExtra("studentId", updatedStudent.id)
                    context.startActivity(intent)
                    (context as? ComponentActivity)?.finish()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Save")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                StudentsRepository.deleteStudent(student.id)
                Toast.makeText(context, "Student deleted", Toast.LENGTH_SHORT).show()

                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
                (context as? ComponentActivity)?.finish()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Delete")
        }
    }
}