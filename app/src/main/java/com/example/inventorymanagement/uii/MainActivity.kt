package com.example.inventorymanagement.uii

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.inventorymanagement.navigation.NavGraph
import com.example.inventorymanagement.navigation.Screen
import com.example.inventorymanagement.ui.theme.ClaudeDarkBrown
import com.example.inventorymanagement.ui.theme.ClaudeWhite
import com.example.inventorymanagement.ui.theme.InventoryManagementTheme
import dagger.hilt.android.AndroidEntryPoint

data class BottomNavItem(
    val icon: ImageVector,
    val label: String,
    val route: String
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InventoryManagementTheme {
                val navController = rememberNavController()

                val bottomNavItems = listOf(
                    BottomNavItem(Icons.Default.Home, "Home", Screen.Home.route),
                    BottomNavItem(Icons.Default.AddCircle, "Scan", Screen.Scan.route),
                    BottomNavItem(Icons.Default.Settings, "Settings", Screen.Settings.route)
                )

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    bottomBar = {
                        NavigationBar(
                            containerColor = ClaudeWhite,
                            contentColor = ClaudeWhite
                        ) {
                            bottomNavItems.forEach { item ->
                                NavigationBarItem(
                                    selected = currentRoute == item.route,
                                    onClick = {
                                        navController.navigate(item.route) {
                                            popUpTo(Screen.Home.route) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = { Icon(item.icon, contentDescription = item.label) },
                                    label = { Text(item.label) },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = ClaudeWhite,
                                        selectedTextColor = ClaudeDarkBrown,
                                        unselectedIconColor = ClaudeDarkBrown,
                                        unselectedTextColor = ClaudeDarkBrown,
                                        indicatorColor = ClaudeDarkBrown
                                    )
                                )
                            }
                        }
                    }
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        NavGraph(navController = navController)
                    }
                }
            }
        }
    }
}