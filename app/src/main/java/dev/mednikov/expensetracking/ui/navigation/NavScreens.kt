package dev.mednikov.expensetracking.ui.navigation

enum class NavScreens {
    HomeScreen,
    LoginScreen,
    SignupScreen,
    CurrentUserScreen,
    AccountsListScreen,
    DashboardScreen,
    CategoriesListScreen,
    OperationsListScreen;

    companion object {
        fun fromRoute(route: String?): NavScreens = when(route?.substringBefore("/")){
            SignupScreen.name -> SignupScreen
            LoginScreen.name -> LoginScreen
            HomeScreen.name -> HomeScreen
            AccountsListScreen.name -> AccountsListScreen
            DashboardScreen.name -> DashboardScreen
            CategoriesListScreen.name -> CategoriesListScreen
            OperationsListScreen.name -> OperationsListScreen
            CurrentUserScreen.name -> CurrentUserScreen
            null -> LoginScreen
            else -> throw IllegalArgumentException("Route is not available")
        }
    }
}