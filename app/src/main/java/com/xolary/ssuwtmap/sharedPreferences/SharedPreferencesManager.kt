package com.xolary.ssuwtmap.sharedPreferences

import android.content.Context

class SharedPreferencesManager(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

    fun saveAuth() {
        sharedPreferences.edit().putBoolean("isAuth", true).apply()
    }

    fun clearAuth() {
        sharedPreferences.edit().remove("isAuth").apply()
    }

    fun getAuthState(): Boolean {
        return sharedPreferences.getBoolean("isAuth", false)
    }
}