package dev.mednikov.expensetracking.models

data class Account(
    var id: String? = null,
    var userId: String? = null,
    val name: String,
    val type: AccountType
)
