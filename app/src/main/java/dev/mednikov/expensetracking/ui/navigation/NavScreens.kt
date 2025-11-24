package dev.mednikov.expensetracking.ui.navigation

enum class NavScreens {
    HomeScreen,
    LoginScreen,
    SignupScreen;

    companion object {
        fun fromRoute(route: String?): NavScreens
                = when(route?.substringBefore("/")){
            SignupScreen.name -> SignupScreen
            LoginScreen.name -> LoginScreen
            HomeScreen.name -> HomeScreen
            null -> LoginScreen
            else -> throw IllegalArgumentException("Route is not available")
        }
    }
}