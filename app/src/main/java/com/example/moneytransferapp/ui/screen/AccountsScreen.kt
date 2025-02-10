package com.example.moneytransferapp.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.moneytransferapp.ui.components.AccountItem
import com.example.moneytransferapp.viewmodel.AccountViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(viewModel: AccountViewModel, navController: NavController) {
    val accounts by viewModel.accounts.observeAsState(listOf())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("transfer") }) {
                Icon(Icons.Default.Add, contentDescription = "Transfer Money")
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(accounts) { account ->
                AccountItem(account = account)
            }
        }
    }
}
