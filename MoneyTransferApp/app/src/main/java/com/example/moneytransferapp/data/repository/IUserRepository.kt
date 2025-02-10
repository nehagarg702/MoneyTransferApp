package com.example.moneytransferapp.data.repository

interface IUserRepository {
    fun saveLoginStatus(isLoggedIn: Boolean)
    fun getLoginStatus(): Boolean
}
