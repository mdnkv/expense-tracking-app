package dev.mednikov.expensetracking.models

import java.math.BigDecimal
import java.time.LocalDate

data class Operation(
    var id: String? =null,
    var userId: String? = null,
    var accountId: String,
    var amount: BigDecimal,
    var date: LocalDate,
    var description: String,
    var operationType: OperationType,
    var categoryId: String? = null,
    var currencyId: String? = null,
    val currency: Currency? = null,
    val category: Category? = null,
    val account: Account? = null
)
