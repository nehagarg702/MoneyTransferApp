package com.example.moneytransferapp.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.moneytransferapp.ui.components.TransactionItem
import com.example.moneytransferapp.viewmodel.TransactionViewModel

@Composable
fun TransactionHistoryScreen(viewModel: TransactionViewModel) {
    val transactions by viewModel.transactions.observeAsState(listOf())
    LazyColumn {
        items(transactions) { transaction ->
            TransactionItem(transaction = transaction)
        }
    }

    LaunchedEffect(true) {
        viewModel.loadTransactions()
    }
}
