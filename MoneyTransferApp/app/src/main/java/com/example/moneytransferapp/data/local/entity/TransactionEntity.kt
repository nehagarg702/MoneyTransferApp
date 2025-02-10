package com.example.moneytransferapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sourceAccountName: String,
    val destinationAccountName: String,
    val amount: Double,
    val timestamp: Long
)
