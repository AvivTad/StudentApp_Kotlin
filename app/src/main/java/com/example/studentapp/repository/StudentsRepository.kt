package com.example.studentapp.repository

import com.example.studentapp.model.Student

object StudentsRepository {

    private val students = mutableListOf<Student>()

    fun getAllStudents(): List<Student> = students

    fun addStudent(student: Student) {
        students.add(student)
    }

    fun updateStudent(updatedStudent: Student) {
        val index = students.indexOfFirst { it.id == updatedStudent.id }
        if (index != -1) {
            students[index] = updatedStudent
        }
    }

    fun deleteStudent(studentId: Int) {
        students.removeIf { it.id == studentId }
    }

    fun getStudentById(studentId: Int): Student? {
        return students.find { it.id == studentId }
    }
}