package dev.mednikov.expensetracking.ui.screens.users

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import dev.mednikov.expensetracking.ui.shared.AppBarActions
import dev.mednikov.expensetracking.ui.shared.ApplicationToolBarComponent
import dev.mednikov.expensetracking.ui.shared.ConfirmDialogComponent
import dev.mednikov.expensetracking.viewmodel.authentication.AuthViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CurrentUserScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel())
{
    val confirmDialogState = rememberSaveable { mutableStateOf(false) }

    Scaffold (
        topBar = {
            ApplicationToolBarComponent(
                title = "My account",
                onAction = {confirmDialogState.value = true},
                onBack = {navController.popBackStack()},
                showBackButton = true,
                actionType = AppBarActions.LOGOUT
            )
        }
    ) {paddingValues ->
        ConfirmDialogComponent (
            dialogState = confirmDialogState,
            label = "Do you want to logout?",
            confirmAction = {
                authViewModel.logout()
            },
            dismissAction = {}
        )
        Column (
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text("My account")
        }
    }
}