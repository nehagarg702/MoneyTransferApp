package com.example.moneytransferapp.data.repository

import com.example.moneytransferapp.data.local.dao.AccountDao
import com.example.moneytransferapp.data.local.entity.AccountEntity

class AccountRepository(private val accountDao: AccountDao) : IAccountRepository {

    override suspend fun getAllAccounts() = accountDao.getAllAccounts()

    override suspend fun updateBalance(accountId: Long, newBalance: Double) {
        accountDao.updateBalance(accountId, newBalance)
    }

    override suspend fun insertMockAccounts() {
        val mockAccounts = listOf(
            AccountEntity(1, "John Doe", 100000.0),
            AccountEntity(2, "Jane Smith", 1545600.0),
            AccountEntity(3, "Alice Brown", 2043200.0),
            AccountEntity(4, "Bob Johnson", 50560.0),
            AccountEntity(5, "Charlie Williams", 12700.0),
            AccountEntity(6, "David Wilson", 305600.0),
            AccountEntity(7, "Emma Thomas", 25030.0),
            AccountEntity(8, "Frank Harris", 18700.0),
            AccountEntity(9, "Grace Martin", 1100.0),
            AccountEntity(10, "Henry White", 900.0)
        )
        accountDao.insert(mockAccounts)
    }
}
