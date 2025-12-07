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
import androidx.navigation.NavController
import dev.mednikov.expensetracking.models.Operation
import dev.mednikov.expensetracking.ui.navigation.NavScreens
import dev.mednikov.expensetracking.ui.shared.AppBarActions
import dev.mednikov.expensetracking.ui.shared.ApplicationToolBarComponent
import dev.mednikov.expensetracking.ui.shared.ConfirmDialogComponent
import dev.mednikov.expensetracking.ui.shared.DetailSectionComponent
import dev.mednikov.expensetracking.ui.shared.EditActionButtonComponent
import dev.mednikov.expensetracking.ui.shared.ItemNotFoundComponent
import dev.mednikov.expensetracking.ui.shared.LoadingPlaceholderComponent
import dev.mednikov.expensetracking.ui.shared.getDateText
import dev.mednikov.expensetracking.ui.shared.getMoneyText
import dev.mednikov.expensetracking.viewmodel.operations.OperationDetailViewModel

@Composable
fun OperationDetailScreen (navController: NavController, operationId: String, viewModel: OperationDetailViewModel = hiltViewModel()) {
    val detailState = viewModel.detailUiState
    val deleteState = viewModel.deleteUiState

    LaunchedEffect(operationId) {
        viewModel.getOperationById(operationId)
    }

    LaunchedEffect(deleteState) {
        if (deleteState.isDeleted) {
            navController.popBackStack()
        }
    }

    when {
        detailState.isLoading -> LoadingPlaceholderComponent()
        !detailState.isExist -> ItemNotFoundComponent(navController)
        detailState.error != null -> ItemNotFoundComponent(navController)
        else -> OperationDetailComponent (navController = navController, operation = detailState.operation!!) {
            viewModel.deleteOperation(operationId)
        }
    }
}

@Composable
fun OperationDetailComponent(navController: NavController, operation: Operation, onDelete: ()->Unit){
    val deleteDialogState = rememberSaveable { mutableStateOf(false) }
//    val dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
    val dateText: String = getDateText(operation.date)

//    val currencyUnit = CurrencyUnit.of(operation.currency!!.code)
//    val amountMonetary = Money.of(currencyUnit, operation.amount)
    val amountText = getMoneyText(operation.amount, operation.currency!!)

    ConfirmDialogComponent (
        dialogState = deleteDialogState,
        label = "Do you want to delete this operation?",
        confirmAction = {
            onDelete()
        },
        dismissAction = {}
    )
    Scaffold (
        topBar = {
            ApplicationToolBarComponent(
                title = "Operation detail",
                onBack = {
                    navController.popBackStack()
                },
                onAction = {
                    deleteDialogState.value = true
                },
                showBackButton = true,
                actionType = AppBarActions.DELETE
            )
        },
        floatingActionButton = {
            EditActionButtonComponent {
                navController.navigate("${NavScreens.OperationUpdateScreen.name}/${operation.id!!}")
            }
        }
    ) { paddingValues ->
        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            DetailSectionComponent(label = "Description", value = operation.description)
            DetailSectionComponent(label = "Date", value = dateText)
            DetailSectionComponent(label = "Amount", value = amountText)
            DetailSectionComponent(label = "Account", value = operation.account!!.name)
            DetailSectionComponent(label = "Category", value = operation.category!!.name)
        }
    }
}