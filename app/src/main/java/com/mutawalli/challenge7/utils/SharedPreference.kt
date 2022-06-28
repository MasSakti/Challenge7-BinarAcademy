package com.mutawalli.challenge7.utils

import android.annotation.SuppressLint
import android.content.Context
import com.mutawalli.challenge7.data.local.UserEntity

class SharedPreference(context: Context) {

    private val pref = "authentication"
    private val sharedPreference = context.getSharedPreferences(pref, Context.MODE_PRIVATE)

    @SuppressLint("CommitPrefEdits")
    fun saveKey(user: UserEntity) {
        val editor = sharedPreference.edit()
        editor.putString("email", user.email)
        editor.putInt("id", user.id)
        editor.putString("password", user.password)
        editor.putString("address", user.address)
        editor.putString("fullname", user.fullname)
        editor.putString("date", user.ttl)
        editor.putString("username", user.username)
        editor.apply()
    }

    @SuppressLint("CommitPrefEdits")
    fun saveKeyState(status: Boolean) {
        val editor = sharedPreference.edit()
        editor.putBoolean("login_status", status)
        editor.apply()
    }

    @SuppressLint("CommitPrefEdits")
    fun getPrefKey(key: String): String? {
        return sharedPreference.getString(key, "data kosong")
    }

    @SuppressLint("CommitPrefEdits")
    fun getPrefKeyId(key: String): Int? {
        return sharedPreference.getInt(key, 0)
    }

    @SuppressLint("CommitPrefEdits")
    fun getPrefKeyStatus(key: String): Boolean {
        return sharedPreference.getBoolean(key, false)
    }

    @SuppressLint("CommitPrefEdits")
    fun clearUsername() {
        val editor = sharedPreference.edit()
        editor.clear()
        editor.apply()
    }
}