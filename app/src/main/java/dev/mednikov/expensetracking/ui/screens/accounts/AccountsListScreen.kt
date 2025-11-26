package dev.mednikov.expensetracking.ui.screens.accounts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import dev.mednikov.expensetracking.ui.shared.AppBarActions
import dev.mednikov.expensetracking.ui.shared.ApplicationToolBarComponent
import dev.mednikov.expensetracking.ui.shared.CreateActionButtonComponent

@Composable
fun AccountsListScreen(navController: NavController){
    Scaffold (
        topBar = {
            ApplicationToolBarComponent(
                title = "Accounts",
                onAction = {},
                actionType = AppBarActions.SEARCH
            )
        },
        floatingActionButton = { CreateActionButtonComponent(onAction = {}) }
    ) {paddingValues ->
        Column (
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text("Accounts")
        }
    }
}