package ru.autodoc.tz.ui.users

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.autodoc.tz.base.BaseViewModel
import ru.autodoc.tz.domain.user.User
import ru.autodoc.tz.domain.user.UserFindByLoginUseCase

import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userFindByLoginUseCase: UserFindByLoginUseCase
) : BaseViewModel() {
    private val _userData = MutableStateFlow<User?>(null)
    val userData: StateFlow<User?> = _userData

    fun getUser(login: String) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            loading.value = true
            _userData.value = userFindByLoginUseCase.execute(login = login)
            loading.value = false
        }
    }
}