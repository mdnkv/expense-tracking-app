package dev.mednikov.expensetracking.models

data class SignupRequest(
    var email: String,
    var password: String,
    var firstName: String,
    var lastName: String
)
