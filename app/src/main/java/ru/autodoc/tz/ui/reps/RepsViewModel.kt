package ru.autodoc.tz.ui.reps

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.autodoc.tz.base.BaseViewModel
import ru.autodoc.tz.domain.rep.Rep
import ru.autodoc.tz.domain.rep.RepFindAllByQueryUseCase
import javax.inject.Inject

@HiltViewModel
class RepsViewModel @Inject constructor(
    private val repFindAllByQueryUseCase: RepFindAllByQueryUseCase
) : BaseViewModel() {

    val reps = MutableStateFlow<PagingData<Rep>?>(null)

    fun getReps(query: String) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            repFindAllByQueryUseCase.execute(query = query)
                .cachedIn(scope = viewModelScope)
                .collect {
                    reps.value = it
                }
        }
    }
}

