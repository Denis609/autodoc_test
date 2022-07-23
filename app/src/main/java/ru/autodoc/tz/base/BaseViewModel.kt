package ru.autodoc.tz.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    val loading = MutableStateFlow<Boolean>(false)

    private val errorChannel = Channel<String>()
    val errorFlow = errorChannel.receiveAsFlow()

    val handler = CoroutineExceptionHandler { _, exception ->
        loading.value = false
        println("CoroutineExceptionHandler got $exception")
        viewModelScope.launch {
            errorChannel.send(element = exception.message.toString())
        }
    }
}
