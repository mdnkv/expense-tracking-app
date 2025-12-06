package dev.mednikov.expensetracking.ui.screens.operations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.mednikov.expensetracking.models.Operation
import dev.mednikov.expensetracking.models.OperationType
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import java.time.format.DateTimeFormatter

@Composable
fun OperationItemComponent(operation: Operation, navController: NavController) {
    val dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
    val dateText: String = operation.date.format(dateFormatter)

    val currencyUnit = CurrencyUnit.of(operation.currency!!.code)
    val amountMonetary = Money.of(currencyUnit, operation.amount)
    val amountText = amountMonetary.toString()

    Column (modifier = Modifier.fillMaxWidth().clickable{
        // todo
    },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {

        // Header
        Row (
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Date
            Text(text = dateText,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyMedium)
            // Category
            if (operation.category != null){
                Text(text = operation.category.name,
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyMedium)
            } else {
                Text(text = "(No category)",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyMedium)
            }
        }

        // Description
        Text(text = operation.description,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Justify,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
            style = MaterialTheme.typography.bodyLarge)

        // Footer
        Row (
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Account
            Text(text = operation.account!!.name,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyMedium)
            // Amount
            Text(text = amountText,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodyMedium,
                color = if (operation.operationType == OperationType.INCOME) Color.Green else Color.Red)
        }

    }
}