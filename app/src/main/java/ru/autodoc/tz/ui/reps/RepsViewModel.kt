package ru.autodoc.tz.ui.reps

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.autodoc.tz.base.BaseViewModel
import ru.autodoc.tz.data.model.Rep
import ru.autodoc.tz.data.repository.reps.RepsRepository
import javax.inject.Inject

@HiltViewModel
class RepsViewModel @Inject constructor(
    private val server: RepsRepository
) : BaseViewModel() {

    val reps = MutableStateFlow<PagingData<Rep>>(PagingData.empty())

    fun getReps(query: String) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            server.getReps(query)
                .cachedIn(viewModelScope)
                .collect {
                    reps.value = it
                }
        }
    }
}

