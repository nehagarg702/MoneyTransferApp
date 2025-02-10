package com.example.moneytransferapp.data.repository

import com.example.moneytransferapp.data.local.entity.AccountEntity

interface IAccountRepository {
    suspend fun getAllAccounts(): List<AccountEntity>
    suspend fun updateBalance(accountId: Long, newBalance: Double)
    suspend fun insertMockAccounts()
}
