package dev.mednikov.expensetracking.ui.screens.authentication

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AuthScreenHeaderComponent(actionText: String) {
    Text(
        text = "Welcome to Expense Tracking App",
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center
    )
    Text(
        text = actionText,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center
    )
}

@Composable
fun AuthScreenFooterComponent(
    buttonText: String,
    linkText: String,
    onButtonClicked: () -> Unit,
    onLinkClicked: () -> Unit){
    Button(onClick = {onButtonClicked()},
        modifier = Modifier.padding(vertical = 15.dp, horizontal = 12.dp).fillMaxWidth()) {
        Text(text = buttonText)
    }
    TextButton(onClick = {
        onLinkClicked()
    }) {
        Text(text =  linkText)
    }

}