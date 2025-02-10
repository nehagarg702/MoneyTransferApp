package com.example.moneytransferapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytransferapp.data.local.entity.TransactionEntity
import com.example.moneytransferapp.data.repository.ITransactionRepository
import kotlinx.coroutines.launch

class TransactionViewModel(private val transactionRepository: ITransactionRepository) : ViewModel() {
    private val _transactions = MutableLiveData<List<TransactionEntity>>()
    val transactions: LiveData<List<TransactionEntity>> get() = _transactions

    fun loadTransactions() {
        viewModelScope.launch {
            _transactions.value = transactionRepository.getAllTransactions()
        }
    }

    fun addTransaction(transaction: TransactionEntity) {
        viewModelScope.launch {
            transactionRepository.addTransaction(transaction)
        }
    }
}
