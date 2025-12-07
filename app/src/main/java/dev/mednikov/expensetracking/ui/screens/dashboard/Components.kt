package dev.mednikov.expensetracking.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.mednikov.expensetracking.models.Currency
import dev.mednikov.expensetracking.models.IncomeExpenseData
import dev.mednikov.expensetracking.ui.shared.getMoneyText
import java.math.BigDecimal

@Composable
fun IncomeExpenseWidgetComponent(data: IncomeExpenseData?){
    val income = data?.income ?: BigDecimal.ZERO
    val expense = data?.expense ?: BigDecimal.ZERO
    val currency = data?.currency ?: Currency(code = "EUR", name = "Euro")

    Card (modifier = Modifier.padding(10.dp)) {
        Column (
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text("Income and expense", style = MaterialTheme.typography.titleLarge)
            Text("Last 30 days", style = MaterialTheme.typography.titleSmall)

            Row (
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Income
                WidgetColumnComponent(label = "Income", amount = income, currency = currency)

                // Expense
                WidgetColumnComponent(label = "Expense", amount = expense, currency = currency)
            }

        }
    }
}

@Composable
fun WidgetColumnComponent(label: String, amount: BigDecimal, currency: Currency){
    val amountText = getMoneyText(amount, currency)
    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        Text(amountText,  style = MaterialTheme.typography.titleMedium)
        Text(label, style = MaterialTheme.typography.bodyMedium)
    }
}