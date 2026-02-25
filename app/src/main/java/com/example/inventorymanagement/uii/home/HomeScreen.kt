package com.example.inventorymanagement.uii.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.inventorymanagement.R
import com.example.inventorymanagement.data.local.entity.Category
import com.example.inventorymanagement.data.local.entity.Product
import com.example.inventorymanagement.navigation.Screen
import com.example.inventorymanagement.ui.theme.ClaudeBrown
import com.example.inventorymanagement.ui.theme.ClaudeCream
import com.example.inventorymanagement.ui.theme.ClaudeDarkBrown
import com.example.inventorymanagement.ui.theme.ClaudeOrange
import com.example.inventorymanagement.ui.theme.ClaudeRed
import com.example.inventorymanagement.ui.theme.ClaudeWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val products by viewModel.products.collectAsState()
    val categories by viewModel.categories.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var isLowStockEnabled by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }


    // Filter logic
    val filteredProducts =
        products
            .filter { product ->
                // Low stock filter
                val matchesLowStock =
                    if (isLowStockEnabled) product.stock < product.minStock else true

                // Category filter
                val matchesCategory = if (selectedCategory != null) {
                    product.categoryId == selectedCategory?.id
                } else true

                // Search filter
                val matchesSearch = if (searchQuery.isNotEmpty()) {
                    product.name.contains(searchQuery, ignoreCase = true)
                } else true

                matchesLowStock && matchesCategory && matchesSearch
            }

    Scaffold(
        containerColor = ClaudeCream,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 15.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "App Logo",
                                modifier = Modifier.size(36.dp)
                            )

                            Text(
                                "Inventory",
                                fontWeight = FontWeight.Bold,
                                color = ClaudeWhite
                            )
                        }


                        Row {
                            if (products.size > 1) {
                                Text(
                                    "${products.size} products in stock",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = ClaudeWhite
                                )
                            } else {
                                Text(
                                    "${products.size} product in stock",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = ClaudeWhite
                                )
                            }
                        }


                    }
                },
                windowInsets = WindowInsets(0),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ClaudeDarkBrown,
                    titleContentColor = ClaudeWhite,
                    navigationIconContentColor = ClaudeWhite,
                    actionIconContentColor = ClaudeWhite
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditProduct.createRoute())
                },
                containerColor = ClaudeOrange,
                contentColor = ClaudeWhite
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Product")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                placeholder = { Text("Search products...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 0.dp)
            ) {
                // Low Stock Chip
                item {
                    FilterChip(
                        selected = isLowStockEnabled,
                        onClick = { isLowStockEnabled = !isLowStockEnabled },
                        label = { Text("Low Stock") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ClaudeOrange,
                            selectedLabelColor = ClaudeWhite,
                            containerColor = ClaudeWhite,
                            labelColor = ClaudeBrown
                        )
                    )
                }

                // All Chips
                item {
                    FilterChip(
                        selected = selectedCategory == null,
                        onClick = { selectedCategory = null },
                        label = { Text("All") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ClaudeOrange,
                            selectedLabelColor = ClaudeWhite,
                            containerColor = ClaudeWhite,
                            labelColor = ClaudeBrown
                        )
                    )
                }

                // Category Chips
                items(categories) { category ->
                    FilterChip(
                        selected = selectedCategory?.id == category.id,
                        onClick = {
                            selectedCategory = if (selectedCategory?.id == category.id) {
                                null
                            } else {
                                category
                            }
                        },
                        label = { Text(category.name) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ClaudeOrange,
                            selectedLabelColor = ClaudeWhite,
                            containerColor = ClaudeWhite,
                            labelColor = ClaudeBrown
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Empty State
            if (filteredProducts.isEmpty()) {
                val (icon, title, subtitle) = when {
                    products.isEmpty() -> Triple(
                        Icons.Default.Inventory2,
                        "No Products Yet",
                        "Tap + to add your first product"
                    )

                    searchQuery.isNotEmpty() -> Triple(
                        Icons.Default.SearchOff,
                        "No Results Found",
                        "Try different search terms"
                    )

                    isLowStockEnabled && selectedCategory != null -> Triple(
                        Icons.Default.Inventory2,
                        "No Low Stock ${selectedCategory?.name}",
                        "All items have sufficient stock"
                    )

                    isLowStockEnabled -> Triple(
                        Icons.Default.CheckCircle,
                        "All Stock Levels Good!",
                        "No products need restocking"
                    )

                    selectedCategory != null -> Triple(
                        Icons.Default.Category,
                        "No ${selectedCategory?.name} Yet",
                        "Add products to this category"
                    )

                    else -> Triple(
                        Icons.Default.Info,
                        "No Results",
                        "Try adjusting filters"
                    )
                }

                EmptyState(icon, title, subtitle)
            } else {
                // Product List
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredProducts) { product ->
                        ProductItem(
                            product = product,
                            onClick = {
                                navController.navigate(
                                    Screen.ProductDetail.createRoute(product.id)
                                )
                            },
                            categories = categories
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    categories: List<Category>,
    onClick: () -> Unit
) {
    val categoryName = categories.find { it.id == product.categoryId }?.name ?: "Uncategorized"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = ClaudeWhite
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = ClaudeDarkBrown
                )

                Spacer(modifier = Modifier.height(5.dp))

                Badge(
                    containerColor = ClaudeDarkBrown,
                    contentColor = ClaudeWhite,
                ) {
                    Text(
                        categoryName,
                        color = ClaudeWhite,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    )
                }

            }

            Column(horizontalAlignment = Alignment.End) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (product.stock < product.minStock) {
                        Badge(
                            containerColor = ClaudeRed,
                            contentColor = ClaudeWhite,
                        ) {
                            Text(
                                "Low",
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 2.dp)
                            )
                        }
                    }

                    Text(
                        "${product.stock}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = ClaudeDarkBrown
                    )
                }
                Text("in stock", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun EmptyState(
    icon: ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}


