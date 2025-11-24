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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.mednikov.expensetracking.ui.navigation.NavScreens
import dev.mednikov.expensetracking.ui.shared.InputFieldComponent

@Preview
@Composable
fun LoginScreen(navController: NavController? = null) {
    val emailState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }

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
                    if (navController != null){
                        navController!!.navigate(NavScreens.HomeScreen.name)
                    }
                },
                onLinkClicked = {
                    if (navController != null){
                        navController!!.navigate(NavScreens.SignupScreen.name)
                    }
                }
            )
        }
    }

}