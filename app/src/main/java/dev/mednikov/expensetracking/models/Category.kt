package dev.mednikov.expensetracking.models

data class Category(
    var id: String? = null,
    var userId: String? = null,
    val name: String
)
