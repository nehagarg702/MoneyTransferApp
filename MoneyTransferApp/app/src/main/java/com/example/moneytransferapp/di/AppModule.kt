package com.example.moneytransferapp.di

import androidx.room.Room
import com.example.moneytransferapp.data.local.AppDatabase
import com.example.moneytransferapp.data.repository.AccountRepository
import com.example.moneytransferapp.data.repository.IAccountRepository
import com.example.moneytransferapp.data.repository.ITransactionRepository
import com.example.moneytransferapp.data.repository.IUserRepository
import com.example.moneytransferapp.data.repository.TransactionRepository
import com.example.moneytransferapp.data.repository.UserRepository
import com.example.moneytransferapp.utils.NetworkUtils
import com.example.moneytransferapp.utils.PreferencesManager
import com.example.moneytransferapp.viewmodel.AccountViewModel
import com.example.moneytransferapp.viewmodel.TransactionViewModel
import com.example.moneytransferapp.viewmodel.UserViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { Room.databaseBuilder(get(), AppDatabase::class.java, "money_transfer_db").build() }
    single { get<AppDatabase>().accountDao() }
    single { get<AppDatabase>().transactionDao() }
    single<IAccountRepository> { AccountRepository(get()) } // AccountRepository is the implementation of IAccountRepository
    single<ITransactionRepository> { TransactionRepository(get()) } // AccountRepository is the implementation of IAccountRepository
    single<IUserRepository> { UserRepository(androidApplication()) } // Use the interface
    single { NetworkUtils(androidApplication()) }
    viewModel { UserViewModel(get(), get()) }
    viewModel { AccountViewModel(get(), get()) }
    viewModel { TransactionViewModel(get()) }
    single { PreferencesManager(androidContext()) }

}
