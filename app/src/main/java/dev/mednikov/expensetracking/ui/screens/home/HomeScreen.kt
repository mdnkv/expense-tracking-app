package dev.mednikov.expensetracking.ui.screens.home

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.mednikov.expensetracking.R
import dev.mednikov.expensetracking.ui.navigation.HomeNavItem
import dev.mednikov.expensetracking.ui.navigation.HomeNavigation
import dev.mednikov.expensetracking.ui.navigation.NavScreens
import dev.mednikov.expensetracking.ui.navigation.bottomNavItems

@Composable
fun HomeScreen(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {BottomNavBar(navController)}
    ) {
        Surface (modifier = Modifier.padding(it)) {
            HomeNavigation(navController)
        }
    }
}

@Composable
fun BottomNavBar(navController: NavController) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    NavigationBar {
        bottomNavItems.forEach { item ->
            val selected = currentDestination
                ?.hierarchy
                ?.any { it.route == item.route } == true
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(NavScreens.HomeScreen.name) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                icon = {
                    val itemIcon = when(item) {
                        HomeNavItem.DashboardItem -> painterResource(R.drawable.ui_home)
                        HomeNavItem.AccountsItem -> painterResource(R.drawable.ui_accounts)
                        HomeNavItem.CategoriesItem -> painterResource(R.drawable.ui_categories)
                        HomeNavItem.OperationsItem -> painterResource(R.drawable.ui_operations)
                    }
                    Icon(
                        painter = itemIcon,
                        modifier = Modifier.width(32.dp).height(32.dp),
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) }
            )
        }
    }
}