package com.example.moneytransferapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moneytransferapp.data.local.dao.AccountDao
import com.example.moneytransferapp.data.local.dao.TransactionDao
import com.example.moneytransferapp.data.local.entity.AccountEntity
import com.example.moneytransferapp.data.local.entity.TransactionEntity

@Database(entities = [AccountEntity::class, TransactionEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
}
