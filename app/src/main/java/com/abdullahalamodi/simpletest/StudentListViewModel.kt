package com.abdullahalamodi.simpletest

import androidx.lifecycle.ViewModel

class StudentListViewModel: ViewModel() {
    private val students = mutableListOf<Student>();

    init {

        for (i in 0 until 6) {
            val student = Student();
            student.number = i + 1000;
            student.name = "student name  #$i";
            student.pass = (i % 3 != 0);
            students += student;
        }

    }

    fun getStudents() = students;

    fun addStudent(student: Student) {
        students += student;
    }

    fun deleteStudent(position: Int) {
        students.removeAt(position);
    }

}