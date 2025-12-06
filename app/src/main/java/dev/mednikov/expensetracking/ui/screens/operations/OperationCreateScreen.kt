package dev.mednikov.expensetracking.ui.screens.operations

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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import dev.mednikov.expensetracking.models.Operation
import dev.mednikov.expensetracking.models.OperationType
import dev.mednikov.expensetracking.ui.shared.AppBarActions
import dev.mednikov.expensetracking.ui.shared.ApplicationToolBarComponent
import dev.mednikov.expensetracking.ui.shared.ConfirmDialogComponent
import dev.mednikov.expensetracking.ui.shared.DateInputComponent
import dev.mednikov.expensetracking.ui.shared.InputFieldComponent
import dev.mednikov.expensetracking.viewmodel.operations.OperationCreateViewModel
import java.math.BigDecimal
import java.time.LocalDate

@Composable
fun OperationCreateScreen(navController: NavController, viewModel: OperationCreateViewModel = hiltViewModel()){
    val uiState = viewModel.uiState
    val confirmDialogState = rememberSaveable { mutableStateOf(false) }
    val descriptionState = rememberSaveable { mutableStateOf("") }
    val amountState = rememberSaveable { mutableStateOf(BigDecimal.ZERO) }
    val dateState = rememberSaveable { mutableStateOf(LocalDate.now()) }
    val typeState = rememberSaveable { mutableStateOf(OperationType.INCOME) }
    val accountState = rememberSaveable { mutableStateOf("") }
    val categoryState = rememberSaveable { mutableStateOf("") }

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect (Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.loadData()
        }
    }

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
                title = "Create operation",
                onAction = {
                    val categoryId = categoryState.value.ifEmpty { null }
                    val accountId = accountState.value
                    val type = typeState.value
                    val payload = Operation(
                        operationType = type,
                        accountId = accountId,
                        categoryId = categoryId,
                        description = descriptionState.value,
                        date = dateState.value,
                        amount = amountState.value
                    )
                    viewModel.createOperation(payload)

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
            InputFieldComponent(label = "Description", state = descriptionState)
            AmountInputComponent(state = amountState)
            DateInputComponent(state = dateState)
            OperationTypeInputComponent(state = typeState)
            OperationAccountSelectComponent(state = accountState, accounts = uiState.accounts)
            OperationCategorySelectComponent(state = categoryState, categories = uiState.categories)
        }
    }

}