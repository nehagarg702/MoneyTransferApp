package com.example.moneytransferapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.moneytransferapp.data.local.entity.AccountEntity

@Dao
interface AccountDao {
    @Insert
    suspend fun insert(account: List<AccountEntity>)

    @Query("SELECT * FROM accounts")
    suspend fun getAllAccounts(): List<AccountEntity>

    @Query("UPDATE accounts SET balance = :newBalance WHERE id = :accountId")
    suspend fun updateBalance(accountId: Long, newBalance: Double)
}
