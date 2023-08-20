package com.awesome.viewmodel.home

import android.util.Log
import com.awesome.entities.repos.AuthRepository
import com.awesome.entities.utils.UnauthorizedException
import com.awesome.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<HomeUiState, HomeEvents>(HomeUiState()) , HomeInteractions {
    init {
        isLoggedIn()
    }

    private fun isLoggedIn(){
        tryToExecute(
            call = authRepository::isLoggedIn,
            onSuccess = ::isLoggedSuccess,
            onError = ::isLoggedError
        )
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