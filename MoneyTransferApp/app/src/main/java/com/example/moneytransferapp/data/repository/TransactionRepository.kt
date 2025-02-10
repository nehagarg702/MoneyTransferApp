package com.example.moneytransferapp.data.repository

import com.example.moneytransferapp.data.local.dao.TransactionDao
import com.example.moneytransferapp.data.local.entity.TransactionEntity

class TransactionRepository(private val transactionDao: TransactionDao) : ITransactionRepository {
    override suspend fun getAllTransactions() = transactionDao.getAllTransactions()

    override suspend fun addTransaction(transaction: TransactionEntity) {
        transactionDao.insert(transaction)
    }
}
