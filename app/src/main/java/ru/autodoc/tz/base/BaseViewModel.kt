package ru.autodoc.tz.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel : ViewModel() {

    val loading = MutableStateFlow<Boolean>(false)

    val handler = CoroutineExceptionHandler { _, exception ->
        loading.value = false
        println("CoroutineExceptionHandler got $exception")
    }
}