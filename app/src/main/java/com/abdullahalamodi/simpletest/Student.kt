package com.abdullahalamodi.simpletest

import java.util.*

data class Student(
    val id: UUID = UUID.randomUUID(),
    var number: Int = 0,
    var name: String = "",
    var pass: Boolean = false
)