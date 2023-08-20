package com.awesome.viewmodel.home

import com.awesome.entities.repos.AuthRepository
import com.awesome.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<HomeUiState, HomeEvents>(HomeUiState()) {

}