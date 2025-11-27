package dev.mednikov.expensetracking.ui.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mednikov.expensetracking.R

@Composable
fun InputFieldComponent (
    label: String,
    state: MutableState<String>,
    isSingleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    OutlinedTextField(
        value = state.value,
        onValueChange = {state.value = it},
        label = { Text(text = label) },
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground),
        modifier = Modifier
            .padding(vertical = 5.dp, horizontal = 12.dp)
            .fillMaxWidth(),
        enabled = true,
        keyboardOptions = KeyboardOptions(keyboardType= keyboardType, imeAction = imeAction)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationToolBarComponent(
    modifier: Modifier = Modifier,
    title: String,
    actionTitle: String = "",
    showBackButton: Boolean = false,
    actionType: AppBarActions = AppBarActions.EDIT,
    onBack: () -> Unit = {},
    onAction: () -> Unit
) {
    TopAppBar(
        title = {
            Row (verticalAlignment = Alignment.CenterVertically) {
                if (showBackButton) {
                    Icon(painter = painterResource(R.drawable.ui_back),
                        contentDescription = "Go back",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .width(32.dp)
                            .height(32.dp)
                            .padding(horizontal = 5.dp)
                            .clickable{onBack()})
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        actions = {
            val actionIcon = when(actionType) {
                AppBarActions.LOGOUT -> painterResource(R.drawable.ui_logout)
                AppBarActions.USER_ACCOUNT -> painterResource(R.drawable.ui_user)
                AppBarActions.EDIT -> painterResource(R.drawable.ui_edit)
                AppBarActions.SEARCH -> painterResource(R.drawable.ui_search)
                AppBarActions.CONFIRM -> painterResource(R.drawable.ui_confirm)
            }
            Icon(painter = actionIcon,
                contentDescription = actionTitle,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp)
                    .padding(horizontal = 5.dp)
                    .clickable{onAction()})
        },
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Composable
fun ConfirmDialogComponent(
    dialogState: MutableState<Boolean>,
    label: String,
    confirmAction: () -> Unit,
    dismissAction: () -> Unit
) {
    if (dialogState.value){
        AlertDialog(
            onDismissRequest = {
                dismissAction()
                dialogState.value = false
            },
            confirmButton = {
                TextButton(onClick = {
                    confirmAction()
                    dialogState.value = false
                }) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                TextButton (onClick = {
                    dismissAction()
                    dialogState.value = false
                }) {
                    Text(text = "No")
                }
            },
            text = {
                Text(text = label, style = MaterialTheme.typography.bodyLarge)
            }
        )
    }
}

@Composable
fun CreateActionButtonComponent(onAction: () -> Unit) {
    FloatingActionButton (onClick = {
        onAction()
    }) {
        Icon(
            painter = painterResource(R.drawable.ui_plus),
            contentDescription = "Add",
            modifier = Modifier
                .width(32.dp)
                .height(32.dp))
    }
}