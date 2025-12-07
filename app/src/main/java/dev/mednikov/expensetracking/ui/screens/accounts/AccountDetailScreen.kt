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
import dev.mednikov.expensetracking.ui.navigation.NavScreens
import dev.mednikov.expensetracking.ui.shared.AppBarActions
import dev.mednikov.expensetracking.ui.shared.ApplicationToolBarComponent
import dev.mednikov.expensetracking.ui.shared.ConfirmDialogComponent
import dev.mednikov.expensetracking.ui.shared.DetailSectionComponent
import dev.mednikov.expensetracking.ui.shared.EditActionButtonComponent
import dev.mednikov.expensetracking.ui.shared.ItemNotFoundComponent
import dev.mednikov.expensetracking.ui.shared.LoadingPlaceholderComponent
import dev.mednikov.expensetracking.viewmodel.accounts.AccountDetailViewModel

@Composable
fun AccountDetailScreen(
    navController: NavController,
    accountId: String,
    viewModel: AccountDetailViewModel = hiltViewModel()) {

    val detailState = viewModel.detailUiState
    val deleteState = viewModel.deleteUiState

    LaunchedEffect(accountId) {
        viewModel.getAccountById(accountId)
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
        else -> AccountDetailComponent (navController = navController, account = detailState.account!!) {
            viewModel.deleteAccount(accountId)
        }
    }
}

@Composable
fun AccountDetailComponent(navController: NavController, account: Account, onDelete:() -> Unit){
    val deleteDialogState = rememberSaveable { mutableStateOf(false) }
    ConfirmDialogComponent (
        dialogState = deleteDialogState,
        label = "Do you want to delete this account?",
        confirmAction = {
            onDelete()
        },
        dismissAction = {}
    )
    Scaffold (
        topBar = {
            ApplicationToolBarComponent(
                title = "Account detail",
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
                navController.navigate("${NavScreens.AccountUpdateScreen.name}/${account.id!!}")
            }
        }
    ) { paddingValues ->
        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            DetailSectionComponent(label = "Account name", value = account.name)
            DetailSectionComponent(label = "Account type", value = when(account.type){
                AccountType.BANK_ACCOUNT -> "Bank"
                AccountType.CASH -> "Cash"
                AccountType.CREDIT_CARD -> "Card"
            })
        }
    }
}