package com.awesome.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.entities.utils.NetworkException
import com.awesome.entities.utils.NullDataException
import com.awesome.entities.utils.ServerException
import com.awesome.entities.utils.UnauthorizedException
import com.awesome.entities.utils.ValidationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

open class BaseViewModel<STATE, EVENT>(state: STATE) : ViewModel() {
    protected val _state by lazy { MutableStateFlow<STATE>(state) }
    val state = _state.asStateFlow()

    protected val _event = MutableSharedFlow<EVENT>()
    val event = _event.asSharedFlow()

    protected fun <T> tryToExecute(
        call: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (e: Throwable) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                call().also(onSuccess)
            } catch (e: ValidationException) {
                onError(e)
            } catch (e: NullDataException) {
                onError(e)
            } catch (e: NetworkException) {
                onError(e)
            } catch (e: ServerException) {
                onError(e)
            } catch (e: UnauthorizedException) {
                onError(e)
            } catch (e: Throwable) {
                onError(e)
            }
        }
    }

    protected fun sendEvent(event: EVENT) {
        viewModelScope.launch(Dispatchers.IO) { _event.emit(event) }
    }

    protected fun <T> collectFlow(flow: Flow<T> , collect: suspend (T) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            flow.collectLatest(collect)
        }
    }
}