package com.abdullahalamodi.simpletest

import androidx.lifecycle.ViewModel

class StudentListViewModel: ViewModel() {
    private val students = mutableListOf<Student>();

    init {

        for (i in 0 until 100) {
            val student = Student();
            student.number = i + 1000;
            student.name = "student name  #$i";
            student.pass = (i % 4 != 0);
            students += student;
        }

    }
    fun getStudents() = students;
}