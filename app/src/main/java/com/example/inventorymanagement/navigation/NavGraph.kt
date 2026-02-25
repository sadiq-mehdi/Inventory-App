package com.example.inventorymanagement.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.inventorymanagement.uii.home.HomeScreen
import com.example.inventorymanagement.uii.home.ProductDetail
import com.example.inventorymanagement.uii.product.AddEditProductScreen
import com.example.inventorymanagement.uii.scan.ScanScreen
import com.example.inventorymanagement.uii.settings.ManageCategoriesScreen
import com.example.inventorymanagement.uii.settings.SettingsScreen

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(route = Screen.ProductDetail.route) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
            if (productId != null) {
                ProductDetail(navController = navController, productId = productId)
            }
        }

        composable(
            route = Screen.AddEditProduct.route,
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val productIdArg = backStackEntry.arguments?.getString("productId")
            val productId = if (productIdArg == "new") null else productIdArg?.toIntOrNull()

            AddEditProductScreen(
                productId = productId,
                navController = navController
            )
        }

        composable(route = Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
        composable(route = Screen.Scan.route) {
            ScanScreen(navController = navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.ManageCategories.route) {
            ManageCategoriesScreen(navController = navController)
        }
    }

}