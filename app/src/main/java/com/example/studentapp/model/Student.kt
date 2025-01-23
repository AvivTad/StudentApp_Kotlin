package com.example.studentapp.model

data class Student(
    val id: Int,
    val name: String,
    var isChecked: Boolean = false,
    val phone: String,
    val address: String
)