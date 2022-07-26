package ru.autodoc.tz.ui.users

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.autodoc.tz.domain.user.User
import ru.autodoc.tz.domain.user.UserFindByLoginUseCase
import ru.autodoc.tz.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userFindByLoginUseCase: UserFindByLoginUseCase
) : BaseViewModel() {

    val userData = MutableStateFlow<User?>(null)

    fun getUser(login: String) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            loading.value = true
            userData.value = userFindByLoginUseCase.execute(login = login)
            loading.value = false
        }
    }
}