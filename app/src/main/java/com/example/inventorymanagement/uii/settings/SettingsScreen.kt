package com.example.inventorymanagement.uii.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.inventorymanagement.navigation.Screen
import com.example.inventorymanagement.ui.theme.ClaudeBrown
import com.example.inventorymanagement.ui.theme.ClaudeCream
import com.example.inventorymanagement.ui.theme.ClaudeDarkBrown
import com.example.inventorymanagement.ui.theme.ClaudeRed
import com.example.inventorymanagement.ui.theme.ClaudeWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier, navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel()
) {

    var showClearDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = ClaudeCream,
        topBar = {
            TopAppBar(
                title = {
                    Text("Settings")
                },
                windowInsets = WindowInsets(0),
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
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
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp)
                .padding(paddingValues)
        ) {
            Card(
                modifier = modifier.padding(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = ClaudeWhite,
                    contentColor = ClaudeBrown
                ),
                border = BorderStroke(1.dp, ClaudeDarkBrown)
            ) {
                OutlinedButton(
                    onClick = {
                        navController.navigate(Screen.ManageCategories.route)
                    }, modifier = modifier
                        .padding(14.dp)
                        .fillMaxWidth(),
                    border = BorderStroke(1.dp, ClaudeDarkBrown)

                ) {
                    Text("Manage Categories")
                }
            }

            Card(
                modifier = modifier.padding(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = ClaudeWhite,
                    contentColor = ClaudeBrown
                ),
                border = BorderStroke(1.dp, ClaudeDarkBrown)
            ) {
                Text(
                    "About The App",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = ClaudeDarkBrown,
                    modifier = modifier.padding(14.dp)
                )
                Text(
                    "Inventory Management helps you track your products using barcode scanning. " +
                            "Easily add, edit, and manage your inventory with a simple and intuitive interface.",
                    color = ClaudeBrown, modifier = modifier.padding(14.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            OutlinedButton(
                onClick = {

                    showClearDialog = true

                }, modifier = modifier
                    .padding(14.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = ClaudeRed
                ), border = BorderStroke(1.dp, ClaudeDarkBrown)
            ) {
                Text("Clear All Data")
            }

            HorizontalDivider()

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Version 1.0.0",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Developed by Sadiq",
                    style = MaterialTheme.typography.bodySmall,
                    color = ClaudeDarkBrown
                )
            }

            if (showClearDialog) {
                AlertDialog(
                    onDismissRequest = { showClearDialog = false },
                    title = { Text("Clear All Data?") },
                    text = { Text("This will delete all products. This action cannot be undone.") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.clearAllProducts()
                                showClearDialog = false
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Color.Red
                            )
                        ) { Text("Clear") }
                    },
                    dismissButton = {
                        TextButton(onClick = { showClearDialog = false }) {
                            Text("Cancel")
                        }
                    }

                )
            }

        }

    }


}