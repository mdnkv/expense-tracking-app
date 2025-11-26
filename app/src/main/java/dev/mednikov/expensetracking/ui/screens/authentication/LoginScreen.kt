package dev.mednikov.expensetracking.ui.screens.authentication

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import dev.mednikov.expensetracking.models.LoginRequest
import dev.mednikov.expensetracking.ui.navigation.NavScreens
import dev.mednikov.expensetracking.ui.shared.InputFieldComponent
import dev.mednikov.expensetracking.viewmodel.authentication.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()) {

    val loginState by loginViewModel.loginState.collectAsState()

    val emailState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }

    LaunchedEffect(loginState.success) {
        if (loginState.success){
            Log.d("LOGINSCREEN", "Login was successful")
            navController.navigate(NavScreens.HomeScreen.name)
        }
    }

    Surface (modifier = Modifier.fillMaxSize()) {
        Column (
            modifier = Modifier.fillMaxSize().padding(horizontal = 25.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            AuthScreenHeaderComponent(actionText = "Please login to continue")
            // Form
            InputFieldComponent(label = "Email address", state = emailState)
            InputFieldComponent(label = "Password", state = passwordState)
            // Footer
            AuthScreenFooterComponent(
                buttonText = "Log in",
                linkText = "I don't have an account yet",
                onButtonClicked = {
                    // todo validate input
                    // Create payload
                    val payload = LoginRequest(
                        email = emailState.value,
                        password = passwordState.value
                    )
                    // Call payload
                    loginViewModel.login(payload)
                },
                onLinkClicked = {
                    navController.navigate(NavScreens.SignupScreen.name)
                }
            )
        }
    }

}