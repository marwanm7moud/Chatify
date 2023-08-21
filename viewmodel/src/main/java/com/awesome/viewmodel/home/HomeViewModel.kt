package com.awesome.viewmodel.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.awesome.entities.repos.AuthRepository
import com.awesome.entities.utils.UnauthorizedException
import com.awesome.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<HomeUiState, HomeEvents>(HomeUiState()) , HomeInteractions {
    init {
        isLoggedIn()
    }

    private fun isLoggedIn(){
        viewModelScope.launch {
            authRepository.isLoggedIn().collectLatest {isLogged->
                _state.update { it.copy(isLogged = isLogged) }
            }
        }
    }

    private fun isLoggedError(throwable: Throwable) {
        when(throwable){
            is UnauthorizedException -> _state.update { it.copy(error = throwable.message) }
        }
    }

    private fun isLoggedSuccess(unit: Unit) {}
    override fun onSessionExpiredConfirm() {
        sendEvent(HomeEvents.NavigateToLoginScreen)
    }
}