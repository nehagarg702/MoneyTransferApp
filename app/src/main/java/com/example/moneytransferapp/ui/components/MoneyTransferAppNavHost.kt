package com.example.moneytransferapp.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moneytransferapp.ui.screen.AccountsScreen
import com.example.moneytransferapp.ui.screen.TransactionHistoryScreen
import com.example.moneytransferapp.ui.screen.TransferScreen
import com.example.moneytransferapp.viewmodel.AccountViewModel
import com.example.moneytransferapp.viewmodel.TransactionViewModel
import org.koin.androidx.compose.get

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoneyTransferAppNavHost(navController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Money Transfer App") },
                actions = {
                    IconButton(onClick = { navController.navigate("transactionHistory")  }) {
                        Icon(Icons.Default.Info, contentDescription = "Menu")
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "accounts",
            modifier = Modifier.padding(paddingValues) // Adjust padding for scaffold
        ) {

            // Accounts screen (entry point for logged-in users)
            composable("accounts") {
                val accountViewModel: AccountViewModel = get()
                AccountsScreen(viewModel = accountViewModel, navController = navController)
            }

            // Transfer screen
            composable("transfer") {
                val accountViewModel: AccountViewModel = get()
                val transactionViewModel: TransactionViewModel = get()
                TransferScreen(
                    viewModel = accountViewModel,
                    transactionViewModel = transactionViewModel,
                    navController = navController
                )
            }

            // Transaction history screen
            composable("transactionHistory") {
                val transactionViewModel: TransactionViewModel = get()
                TransactionHistoryScreen(viewModel = transactionViewModel)
            }
        }
    }
}


