package com.example.inventorymanagement.uii.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
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
fun AddEditProductScreen(
    productId: Int?,
    navController: NavHostController,
    viewModel: AddEditProductViewModel = hiltViewModel()
) {

    val scannedBarcode = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<String>("scanned_barcode")

    val productName by viewModel.productName.collectAsState()
    val barcode by viewModel.barcode.collectAsState()
    val stock by viewModel.stock.collectAsState()
    val selectedCategoryId by viewModel.selectedCategoryId.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val minStock by viewModel.minStock.collectAsState()
    var expandedDropdown by remember { mutableStateOf(false) }


    LaunchedEffect(productId) {
        productId?.let {
            viewModel.loadProduct(it)
        }
    }

    // Pre-fill barcode if scanned
    LaunchedEffect(scannedBarcode) {
        scannedBarcode?.let { barcode ->
            viewModel.updateBarcode(barcode)
            navController.previousBackStackEntry?.savedStateHandle?.remove<String>("scanned_barcode")
        }
    }

    Scaffold(
        containerColor = ClaudeCream,
        topBar = {
            TopAppBar(
                title = {
                    Text(if (productId == null) "Add Product" else "Edit Product")
                },
                windowInsets = WindowInsets(0),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ClaudeDarkBrown,
                    titleContentColor = ClaudeWhite,
                    navigationIconContentColor = ClaudeWhite,
                    actionIconContentColor = ClaudeWhite
                ),
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Name field
            OutlinedTextField(
                value = productName,
                onValueChange = {
                    viewModel.updateProductName(it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Product Name *") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words
                )
            )

            // Barcode field
            OutlinedTextField(
                value = barcode,
                onValueChange = { viewModel.updateBarcode(it) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Enter manually") }
            )

            // Stock field
            OutlinedTextField(
                value = stock,
                onValueChange = { viewModel.updateStock(it) },
                label = { Text("Stock *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("0") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            // Minimum stock field
            OutlinedTextField(
                value = minStock,
                onValueChange = { viewModel.updateMinStock(it) },
                label = { Text("Minimum stock") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("5") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            // Category dropdown
            ExposedDropdownMenuBox(
                expanded = expandedDropdown,
                onExpandedChange = { expandedDropdown = it }
            ) {
                OutlinedTextField(
                    value = categories.find { it.id == selectedCategoryId }?.name
                        ?: "Select Category *",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expandedDropdown,
                    onDismissRequest = { expandedDropdown = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.name) },
                            onClick = {
                                viewModel.updateSelectedCategory(category.id)
                                expandedDropdown = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            // Save Button
            Button(
                onClick = {
                    viewModel.saveProduct(productId) {
                        if (productId == null) {

                            navController.navigate(Screen.Home.route)
                        } else {
                            navController.popBackStack()
                        }

                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = productName.isNotBlank() && selectedCategoryId != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = ClaudeOrange,
                    contentColor = ClaudeWhite
                )
            ) {
                Text(if (productId == null) "Add Product" else "Save Changes")
            }
        }

    }

}