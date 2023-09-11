package com.awesome.viewmodel.chooseMembers

import com.awesome.viewmodel.search.UserUiState

interface ChooseMemberInteraction {
    fun onSearchInputChanged(input:String)
    fun onClickUser(userUiState: UserUiState)
    fun onClickDone()
}