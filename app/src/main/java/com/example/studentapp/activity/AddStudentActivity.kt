package com.example.studentapp.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.studentapp.R
import com.example.studentapp.model.Student
import com.example.studentapp.repository.StudentsRepository
import com.example.studentapp.ui.theme.StudentAppTheme
import androidx.compose.ui.platform.LocalContext
import android.content.Intent


class AddStudentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StudentAppTheme {
                AddStudentScreen()
            }
        }
    }
}

@Composable
fun AddStudentScreen() {
    var id by remember { mutableStateOf(TextFieldValue("")) }
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var phone by remember { mutableStateOf(TextFieldValue("")) }
    var address by remember { mutableStateOf(TextFieldValue("")) }
    var isChecked by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_student_pic),
            contentDescription = "Student Image",
            modifier = Modifier.size(128.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Add New Student", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = id,
            onValueChange = { id = it },
            label = { Text("Student ID") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Mark as Checked")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val studentId = id.text.toIntOrNull()
            if (studentId != null && name.text.isNotEmpty() && phone.text.isNotEmpty() && address.text.isNotEmpty()) {
                if (StudentsRepository.getStudentById(studentId) != null) {
                    Toast.makeText(
                        context,
                        "Student ID already exists. Please enter a unique ID.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }
                val newStudent = Student(
                    id = studentId,
                    name = name.text,
                    phone = phone.text,
                    address = address.text,
                    isChecked = isChecked
                )
                StudentsRepository.addStudent(newStudent)
                Toast.makeText(
                    context,
                    "Student added successfully!",
                    Toast.LENGTH_SHORT
                ).show()

                val intent = Intent(context, StudentsListActivity::class.java)
                context.startActivity(intent)
                (context as? ComponentActivity)?.finish()
            } else {
                Toast.makeText(
                    context,
                    "Please fill in all fields with valid data.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }) {
            Text("Add Student")
        }
    }
}