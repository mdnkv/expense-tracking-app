package dev.mednikov.expensetracking.ui.shared

import dev.mednikov.expensetracking.models.Currency
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun getMoneyText(amount: BigDecimal, currency: Currency): String{
    val currencyUnit = CurrencyUnit.of(currency.code)
    val amountMonetary = Money.of(currencyUnit, amount)
    return amountMonetary.toString()
}

fun getDateText(date: LocalDate): String{
    val dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
    return date.format(dateFormatter)
}