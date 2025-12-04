package dev.mednikov.expensetracking.ui.screens.accounts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.InputChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import dev.mednikov.expensetracking.models.Account
import dev.mednikov.expensetracking.models.AccountType
import dev.mednikov.expensetracking.ui.navigation.NavScreens
import dev.mednikov.expensetracking.ui.shared.AppBarActions
import dev.mednikov.expensetracking.ui.shared.ApplicationToolBarComponent
import dev.mednikov.expensetracking.ui.shared.CreateActionButtonComponent
import dev.mednikov.expensetracking.ui.shared.EmptyListPlaceholderComponent
import dev.mednikov.expensetracking.ui.shared.LoadingPlaceholderComponent
import dev.mednikov.expensetracking.viewmodel.accounts.AccountListViewModel

@Composable
fun AccountListScreen(navController: NavController, viewModel: AccountListViewModel = hiltViewModel()){

    val uiState = viewModel.uiState

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect (Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.getAccounts()
        }
    }

    when {
        uiState.isLoading -> LoadingPlaceholderComponent()
        else -> AccountListComponent(uiState.accounts, navController)
    }

}

@Composable
fun AccountListComponent(accounts: List<Account>, navController: NavController){
    Scaffold (
        topBar = {
            ApplicationToolBarComponent(
                title = "Accounts",
                onAction = {},
                actionType = AppBarActions.SEARCH
            )
        },
        floatingActionButton = { CreateActionButtonComponent(onAction = {
            navController.navigate(NavScreens.AccountCreateScreen.name)
        }) }
    ) {paddingValues ->
        if (accounts.isEmpty()){
            EmptyListPlaceholderComponent(comment = "No accounts found")
        } else {
            LazyColumn (
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentPadding = PaddingValues(vertical = 10.dp, horizontal = 10.dp)
            ) {
                items(accounts) {account ->
                    AccountItemComponent(account, navController)
                }
            }
        }
    }
}

@Composable
fun AccountItemComponent (account: Account, navController: NavController){
    Row (
        modifier = Modifier.fillMaxWidth().clickable{
            navController.navigate("${NavScreens.AccountDetailScreen.name}/${account.id}")
        },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = account.name, modifier = Modifier.padding(horizontal = 10.dp, vertical = 12.dp))
        InputChip(
            label = {
                Row (horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = when(account.type){
                            AccountType.BANK_ACCOUNT -> "Bank"
                            AccountType.CASH -> "Cash"
                            AccountType.CREDIT_CARD -> "Card"
                        }
                    )
                }
            },
            selected = false,
            onClick = {}
        )
    }
}