package com.example.inventorymanagement.navigation

sealed class Screen(val route: String){
    object Home: Screen("home")
    object Scan: Screen("scan")
    object Settings: Screen("settings")
    object ProductDetail: Screen("product_detail/{productId}"){
        fun createRoute(productId: Int) = "product_detail/$productId"
    }
    object AddEditProduct: Screen("add_edit_product/{productId}") {
        fun createRoute(productId: Int? = null): String {
            return if (productId != null) {
                "add_edit_product/$productId"
            } else {
                "add_edit_product/new"
            }
        }
    }
    object ManageCategories: Screen("manage_categories")
}