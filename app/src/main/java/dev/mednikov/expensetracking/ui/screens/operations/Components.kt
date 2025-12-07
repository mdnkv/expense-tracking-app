package dev.mednikov.expensetracking.ui.screens.operations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.mednikov.expensetracking.models.Account
import dev.mednikov.expensetracking.models.Category
import dev.mednikov.expensetracking.models.Operation
import dev.mednikov.expensetracking.models.OperationType
import dev.mednikov.expensetracking.ui.navigation.NavScreens
import dev.mednikov.expensetracking.ui.shared.getDateText
import dev.mednikov.expensetracking.ui.shared.getMoneyText
import java.math.BigDecimal

@Composable
fun OperationItemComponent(operation: Operation, navController: NavController) {
//    val dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
//    val dateText: String = operation.date.format(dateFormatter)

//    val currencyUnit = CurrencyUnit.of(operation.currency!!.code)
//    val amountMonetary = Money.of(currencyUnit, operation.amount)
    val dateText = getDateText(operation.date)
    val amountText = getMoneyText(operation.amount, operation.currency!!)

    Column (modifier = Modifier.fillMaxWidth().clickable{
        navController.navigate("${NavScreens.OperationDetailScreen.name}/${operation.id!!}")
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

@Composable
fun OperationTypeInputComponent(state: MutableState<OperationType>){
    Column (modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
        Text(text = "Operation type", style = MaterialTheme.typography.bodyMedium )
        FlowRow (
            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            InputChip(
                onClick = {state.value = OperationType.INCOME},
                label = {
                    Row (horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Income")
                    }
                },
                selected = state.value == OperationType.INCOME
            )
            InputChip(
                onClick = {state.value = OperationType.EXPENSE},
                label = {
                    Row (horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Expense")
                    }
                },
                selected = state.value == OperationType.EXPENSE
            )

        }
    }
}

@Composable
fun OperationAccountSelectComponent(state: MutableState<String>, accounts: List<Account>){
    if (accounts.isEmpty()){
        Text(text = "Cannot load accounts", style = MaterialTheme.typography.bodyMedium )
    } else {
        Column (modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
            Text(text = "Account", style = MaterialTheme.typography.bodyMedium )
            FlowRow (
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                accounts.forEach { account ->
                    InputChip(
                        onClick = { state.value = account.id!! },
                        label = { Text(text = account.name) },
                        selected = state.value == account.id!!
                    )
                }
            }
        }
    }
}

@Composable
fun OperationCategorySelectComponent(state: MutableState<String>, categories: List<Category>){
    if (categories.isEmpty()){
        Text(text = "Cannot load categories", style = MaterialTheme.typography.bodyMedium )
    } else {
        Column (modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
            Text(text = "Category", style = MaterialTheme.typography.bodyMedium )
            FlowRow (
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    InputChip(
                        onClick = { state.value = category.id!! },
                        label = { Text(text = category.name) },
                        selected = state.value == category.id!!
                    )
                }
            }
        }
    }
}

@Composable
fun AmountInputComponent (
    state: MutableState<BigDecimal>,
    imeAction: ImeAction = ImeAction.Done
) {
    OutlinedTextField(
        value = state.value.toString(),
        onValueChange = {state.value = BigDecimal(it)},
        label = { Text(text = "Amount") },
        singleLine = true,
        textStyle = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground),
        modifier = Modifier
            .padding(vertical = 5.dp, horizontal = 12.dp)
            .fillMaxWidth(),
        enabled = true,
        keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Number, imeAction = imeAction)
    )
}