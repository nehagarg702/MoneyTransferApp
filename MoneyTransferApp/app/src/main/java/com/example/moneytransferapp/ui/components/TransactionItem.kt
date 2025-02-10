package com.example.moneytransferapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moneytransferapp.data.local.entity.TransactionEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TransactionItem(transaction: TransactionEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Transfer from ${transaction.sourceAccountName} to ${transaction.destinationAccountName}",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Amount: Rs.${transaction.amount}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Date: ${SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US).format(Date(transaction.timestamp))}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
