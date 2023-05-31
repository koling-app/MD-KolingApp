package com.capstone.koling.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.capstone.koling.SessionPreferences
import com.capstone.koling.SupportRepository
import com.capstone.koling.service.api.ApiConfig


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("token")

object Injection {
    fun provideRepository(context: Context): SupportRepository {
        val preferences = SessionPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return SupportRepository.getInstance(preferences, apiService)
    }
}