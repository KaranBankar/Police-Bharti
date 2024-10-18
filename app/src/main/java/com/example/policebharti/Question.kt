package com.example.policebharti

data class Question(
    val id: Int,
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)
