package dev.mednikov.expensetracking.ui.screens.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import dev.mednikov.expensetracking.models.SignupRequest
import dev.mednikov.expensetracking.ui.navigation.NavScreens
import dev.mednikov.expensetracking.ui.shared.InputFieldComponent
import dev.mednikov.expensetracking.viewmodel.authentication.SignupViewModel

@Composable
fun SignupScreen(navController: NavController, signupViewModel: SignupViewModel = hiltViewModel()) {
    val emailState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }
    val firstNameState = rememberSaveable { mutableStateOf("") }
    val lastNameState = rememberSaveable { mutableStateOf("") }

    Surface (modifier = Modifier.fillMaxSize()) {
        Column (
            modifier = Modifier.fillMaxSize().padding(horizontal = 25.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            AuthScreenHeaderComponent(actionText = "It takes a moment to create an account")
            // Form
            InputFieldComponent(label = "Email address", state = emailState)
            InputFieldComponent(label = "Password", state = passwordState)
            InputFieldComponent(label = "First name", state = firstNameState)
            InputFieldComponent(label = "Last name", state = lastNameState)
            // Footer
            AuthScreenFooterComponent(
                buttonText = "Sign up",
                linkText = "I already have an account",
                onButtonClicked = {
                    // Todo validate inputs
                    // Create payload
                    val payload = SignupRequest(
                        email = emailState.value,
                        password = passwordState.value,
                        firstName = firstNameState.value,
                        lastName = lastNameState.value
                    )
                    // Call view model
                    signupViewModel.signup(payload)
                    // Todo Go to Login screen if success

                },
                onLinkClicked = {navController.navigate(NavScreens.LoginScreen.name)}
            )

        }
    }
}