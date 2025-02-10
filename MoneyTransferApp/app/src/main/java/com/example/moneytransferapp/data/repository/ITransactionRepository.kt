package com.example.moneytransferapp.data.repository

import com.example.moneytransferapp.data.local.entity.TransactionEntity

interface ITransactionRepository {
    suspend fun getAllTransactions(): List<TransactionEntity>
    suspend fun addTransaction(transaction: TransactionEntity)
}