package dev.mednikov.expensetracking.ui.screens.users

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import dev.mednikov.expensetracking.ui.shared.AppBarActions
import dev.mednikov.expensetracking.ui.shared.ApplicationToolBarComponent
import dev.mednikov.expensetracking.ui.shared.ConfirmDialogComponent
import dev.mednikov.expensetracking.ui.shared.InputFieldComponent
import dev.mednikov.expensetracking.viewmodel.authentication.AuthViewModel
import dev.mednikov.expensetracking.viewmodel.users.CurrentUserViewModel

@Composable
fun CurrentUserScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    currentUserViewModel: CurrentUserViewModel = hiltViewModel())
{
    val confirmDialogState = rememberSaveable { mutableStateOf(false) }
    val firstNameState = rememberSaveable { mutableStateOf("") }
    val lastNameState = rememberSaveable { mutableStateOf("") }

    val currentUserUiState = currentUserViewModel.uiState

    LaunchedEffect(currentUserUiState.isLoading) {
        if (!currentUserUiState.isLoading) {
            if (currentUserUiState.user != null){
                val user = currentUserUiState.user
                firstNameState.value = user.firstName
                lastNameState.value = user.lastName
            }
        }
    }

    Scaffold (
        topBar = {
            ApplicationToolBarComponent(
                title = "My account",
                onAction = {
                    if (currentUserUiState.user != null){
                        val payload = currentUserUiState.user.copy(
                            firstName = firstNameState.value,
                            lastName = lastNameState.value
                        )
                        currentUserViewModel.updateUser(payload)
                    }
                },
                onBack = {navController.popBackStack()},
                showBackButton = true,
                actionType = AppBarActions.CONFIRM
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
            // Form
            InputFieldComponent(label = "First name", state = firstNameState)
            InputFieldComponent(label = "Last name", state = lastNameState)

            // Logout button
            Button(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 20.dp),
                onClick = {confirmDialogState.value = true}
            ) {
                Text("Logout")
            }
        }
    }
}