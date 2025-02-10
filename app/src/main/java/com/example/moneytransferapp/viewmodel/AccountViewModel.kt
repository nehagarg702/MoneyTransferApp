package com.example.moneytransferapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytransferapp.data.local.entity.AccountEntity
import com.example.moneytransferapp.data.repository.IAccountRepository
import com.example.moneytransferapp.utils.PreferencesManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AccountViewModel(private val accountRepository: IAccountRepository, private val preferencesManager: PreferencesManager) : ViewModel() {
    private val _accounts = MutableLiveData<List<AccountEntity>>()
    val accounts: LiveData<List<AccountEntity>> get() = _accounts

    init {
        viewModelScope.launch {
            val isFirstRun = preferencesManager.isFirstRun.first()
            if (isFirstRun) {
                accountRepository.insertMockAccounts()
                preferencesManager.setFirstRunCompleted() // Mark first run as completed
            }
            loadAccounts()
        }
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            _accounts.value = accountRepository.getAllAccounts()
        }
    }

    fun updateAccountBalance(accountId: Long, newBalance: Double) {
        viewModelScope.launch {
            accountRepository.updateBalance(accountId, newBalance)
        }
    }
}

