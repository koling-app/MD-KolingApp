package com.capstone.koling

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.koling.utils.Injection
import com.capstone.koling.ui.login.LoginViewModel
import com.capstone.koling.SupportRepository


class ViewModelFactory(private val repo: SupportRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
//            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
//                HomeViewModel(repo) as T
//            }
//            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
//                RegisterViewModel(repo) as T
//            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repo) as T
            }
//            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> {
//                AddStoryViewModel(repo) as T
//            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
        }
    }
}