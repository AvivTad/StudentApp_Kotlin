package com.example.studentapp.repository

import com.example.studentapp.model.Student

object StudentsRepository {

    private val studentsList = mutableListOf<Student>(
                Student(id = 1, name = "Aviv Tadmor", isChecked = false,phone = "050-0000000", address = "Jerusalem"),
                Student(id = 2, name = "Gil Salton", isChecked = false,phone = "052-2222222", address = "Modiin"),
                Student(id = 3, name = "Yaron Levi", isChecked = false, phone = "054-4444444", address = "Tel Aviv")

    )

fun getAllStudents(): List<Student> = studentsList

fun addStudent(student: Student) {
    studentsList.add(student)
}

fun updateStudent(updatedStudent: Student) {
    val index = studentsList.indexOfFirst { it.id == updatedStudent.id }
    if (index != -1) {
        studentsList[index] = updatedStudent
    }
}

fun deleteStudent(studentId: Int) {
    studentsList.removeIf { it.id == studentId }
}

fun getStudentById(studentId: Int): Student? {
    return studentsList.find { it.id == studentId }
}

fun updateStudentCheckedStatus(studentId: Int, isChecked: Boolean) {
    val student = getStudentById(studentId)
    if (student != null) {
        student.isChecked = isChecked
    }
}
}