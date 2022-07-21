package ru.autodoc.tz.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.autodoc.tz.data.model.Reps
import ru.autodoc.tz.data.repository.reps.RepsRepository
import javax.inject.Inject

@HiltViewModel
class RepsViewModel @Inject constructor(
    private val server: RepsRepository
) : ViewModel() {

    val reps = MutableStateFlow<PagingData<Reps>>(PagingData.empty())

    fun getReps(query: String) {
        viewModelScope.launch {
            server.getReps(query)
                .cachedIn(viewModelScope)
                .collect {
                    reps.value = it
                }
        }
    }
}

