package dev.mednikov.expensetracking.ui.screens.accounts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import dev.mednikov.expensetracking.models.Account
import dev.mednikov.expensetracking.models.AccountType
import dev.mednikov.expensetracking.ui.shared.AccountTypeInputComponent
import dev.mednikov.expensetracking.ui.shared.AppBarActions
import dev.mednikov.expensetracking.ui.shared.ApplicationToolBarComponent
import dev.mednikov.expensetracking.ui.shared.ConfirmDialogComponent
import dev.mednikov.expensetracking.ui.shared.InputFieldComponent
import dev.mednikov.expensetracking.viewmodel.accounts.AccountCreateViewModel

@Composable
fun AccountCreateScreen(navController: NavController, viewModel: AccountCreateViewModel = hiltViewModel()){
    val uiState = viewModel.uiState

    val confirmDialogState = rememberSaveable { mutableStateOf(false) }

    val nameState = rememberSaveable { mutableStateOf("") }
    val typeState = rememberSaveable { mutableStateOf(AccountType.CASH) }

    LaunchedEffect(uiState.isCreated) {
        if (uiState.isCreated) {
            navController.popBackStack()
        }
    }

    ConfirmDialogComponent (
        dialogState = confirmDialogState,
        label = "Do you want to quit without saving?",
        confirmAction = {
            navController.popBackStack()
        },
        dismissAction = {}
    )

    Scaffold(
        topBar = {
            ApplicationToolBarComponent(
                title = "Create account",
                onAction = {
                    if (nameState.value.isNotEmpty()) {
                        val payload = Account(name = nameState.value, type = typeState.value)
                        viewModel.createAccount(payload)
                    }
                },
                actionType = AppBarActions.CONFIRM,
                onBack = {confirmDialogState.value = true},
                showBackButton = true
            )
        }
    ) { paddingValues ->
        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            InputFieldComponent(
                label = "Name",
                state = nameState
            )
            AccountTypeInputComponent(state = typeState)
        }
    }
}