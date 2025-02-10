package com.example.moneytransferapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moneytransferapp.data.local.entity.AccountEntity
import com.example.moneytransferapp.data.local.entity.TransactionEntity
import com.example.moneytransferapp.viewmodel.AccountViewModel
import com.example.moneytransferapp.viewmodel.TransactionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferScreen(
    viewModel: AccountViewModel,
    transactionViewModel: TransactionViewModel,
    navController: NavController
) {
    val accounts by viewModel.accounts.observeAsState(listOf())
    val sourceAccount = remember { mutableStateOf<AccountEntity?>(null) }
    val destinationAccount = remember { mutableStateOf<AccountEntity?>(null) }
    val transferAmount = remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Select Source Account", style = MaterialTheme.typography.headlineSmall)
        AccountDropdown(
            accounts = accounts.filter { it != destinationAccount.value },
            selectedAccount = sourceAccount.value,
            onAccountSelected = {
                sourceAccount.value = it
                validateAmount(transferAmount, sourceAccount, errorMessage)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Select Destination Account", style = MaterialTheme.typography.headlineSmall)
        AccountDropdown(
            accounts = accounts.filter { it != sourceAccount.value },
            selectedAccount = destinationAccount.value,
            onAccountSelected = {
                destinationAccount.value = it
                validateAmount(transferAmount, sourceAccount, errorMessage)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Enter Amount", style = MaterialTheme.typography.headlineSmall)
        TextField(
            value = transferAmount.value,
            onValueChange = {
                transferAmount.value = it
                validateAmount(transferAmount, sourceAccount, errorMessage)
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("Enter Amount", style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray))
            }
        )

        errorMessage.value?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { showDialog = true },
            modifier = Modifier.fillMaxWidth(),
            enabled = sourceAccount.value != null && destinationAccount.value != null && errorMessage.value == null && transferAmount.value.isNotEmpty()
        ) {
            Text("Transfer")
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirm Transfer") },
            text = {
                Column {
                    Text("Source Account: ${sourceAccount.value?.name}")
                    Text("Destination Account: ${destinationAccount.value?.name}")
                    Text("Amount: Rs. ${transferAmount.value}")
                }
            },
            confirmButton = {
                Button(onClick = {
                    val amount = transferAmount.value.toDoubleOrNull()
                    if (sourceAccount.value != null && destinationAccount.value != null && amount != null && amount > 0 && amount <= sourceAccount.value!!.balance) {
                        val newSourceBalance = sourceAccount.value!!.balance - amount
                        val newDestinationBalance = destinationAccount.value!!.balance + amount

                        viewModel.updateAccountBalance(sourceAccount.value!!.id, newSourceBalance)
                        viewModel.updateAccountBalance(destinationAccount.value!!.id, newDestinationBalance)

                        val transaction = TransactionEntity(
                            sourceAccountName = sourceAccount.value!!.name,
                            destinationAccountName = destinationAccount.value!!.name,
                            amount = amount,
                            timestamp = System.currentTimeMillis()
                        )
                        transactionViewModel.addTransaction(transaction)

                        navController.popBackStack()
                    }
                    showDialog = false
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDropdown(
    accounts: List<AccountEntity>,
    selectedAccount: AccountEntity?,
    onAccountSelected: (AccountEntity) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedText = selectedAccount?.name ?: "Select an Account"

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown Icon")
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            accounts.forEach { account ->
                DropdownMenuItem(
                    text = { Text(account.name) },
                    onClick = {
                        onAccountSelected(account)
                        expanded = false
                    }
                )
            }
        }
    }
}

fun validateAmount(
    transferAmount: MutableState<String>,
    sourceAccount: MutableState<AccountEntity?>,
    errorMessage: MutableState<String?>
) {
    val amount = transferAmount.value.toDoubleOrNull()
    if (amount != null && amount <= 0) {
        errorMessage.value = "Invalid transfer amount"
    } else if (amount != null && sourceAccount.value != null && amount > sourceAccount.value!!.balance) {
        errorMessage.value = "Amount exceeds source account balance"
    } else {
        errorMessage.value = null
    }
}




