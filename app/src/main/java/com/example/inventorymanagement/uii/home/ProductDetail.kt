package com.example.inventorymanagement.uii.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.inventorymanagement.navigation.Screen
import com.example.inventorymanagement.ui.theme.ClaudeCream
import com.example.inventorymanagement.ui.theme.ClaudeDarkBrown
import com.example.inventorymanagement.ui.theme.ClaudeOrange
import com.example.inventorymanagement.ui.theme.ClaudeWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetail(
    productId: Int,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {

    val product by viewModel.product.collectAsState()
    val category by viewModel.categoryName.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    Scaffold(
        containerColor = ClaudeCream,
        topBar = {
            TopAppBar(
                title = { Text("Product Details") },
                windowInsets = WindowInsets(0),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ClaudeDarkBrown,
                    titleContentColor = ClaudeWhite,
                    navigationIconContentColor = ClaudeWhite,
                    actionIconContentColor = ClaudeWhite
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },

                )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            if (product == null) {
                Text("Loading...")
            } else {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .padding(horizontal = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = ClaudeWhite
                    )
                ) {

                    Column(modifier = modifier.padding(16.dp)) {
                        Text(
                            product!!.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = ClaudeDarkBrown
                        )

                        Spacer(modifier = modifier.height(8.dp))

                        Badge(
                            containerColor = ClaudeDarkBrown,
                            contentColor = ClaudeWhite,
                        ) {
                            Text(
                                text = category ?: "Uncategorized",
                                modifier = Modifier.padding(horizontal = 6.dp),
                            )
                        }


                        Spacer(modifier = modifier.height(16.dp))

                        Text(
                            "Barcode: ${product!!.barcode ?: "N/A"}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium,
                            color = ClaudeDarkBrown
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            "Minimum Stock : ${product!!.minStock}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium,
                            color = ClaudeDarkBrown
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            "Stock:",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium,
                            color = ClaudeDarkBrown
                        )

                        Spacer(modifier = modifier.height(8.dp))

                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            IconButton(onClick = {
                                if (product!!.stock > 0) {
                                    viewModel.updateStock(product!!.stock - 1)
                                }
                            }) {
                                Icon(Icons.Filled.Remove, contentDescription = "Decrease")
                            }

                            Text(
                                text = "${product!!.stock}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = ClaudeDarkBrown,
                                modifier = Modifier.padding(horizontal = 32.dp)
                            )

                            IconButton(onClick = {
                                viewModel.updateStock(product!!.stock + 1)
                            }) {
                                Icon(Icons.Filled.Add, contentDescription = "Increase")
                            }
                        }
                    }
                }

                Spacer(modifier = modifier.height(20.dp))

                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            navController.navigate(
                                Screen.AddEditProduct.createRoute(productId = productId)
                            )

                        },
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = ClaudeDarkBrown
                        ), border = BorderStroke(1.dp, ClaudeDarkBrown)

                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Edit Product")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = { showDialog = true },
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = ClaudeWhite,
                            containerColor = ClaudeOrange
                        )
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Delete Product")
                    }
                }
            }
        }
    }

    if (showDialog) {
        DeleteDialog(
            productName = product?.name ?: "",
            onDismiss = { showDialog = false },
            onSuccess = {
                viewModel.deleteProduct {
                    navController.popBackStack()
                }
                showDialog = false
            }
        )
    }
}

@Composable
fun DeleteDialog(
    productName: String,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete Product") },
        text = {
            Text("Are you sure you want to delete \"$productName\"? This action cannot be undone.")
        },
        confirmButton = {
            TextButton(
                onClick = onSuccess,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}