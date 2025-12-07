package dev.mednikov.expensetracking.models

import java.math.BigDecimal

data class IncomeExpenseData (
    val currency: Currency,
    val income: BigDecimal,
    val expense: BigDecimal
)