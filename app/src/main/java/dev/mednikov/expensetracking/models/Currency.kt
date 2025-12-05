package dev.mednikov.expensetracking.models

data class Currency(
    var id: String? = null,
    var userId: String? = null,
    val name: String,
    val code: String
)
