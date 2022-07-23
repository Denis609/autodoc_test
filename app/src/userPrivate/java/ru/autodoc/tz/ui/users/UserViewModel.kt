package ru.autodoc.tz.ui.users

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.autodoc.tz.base.BaseViewModel
import ru.autodoc.tz.data.model.User
import ru.autodoc.tz.data.repository.users.UsersRepository
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val server: UsersRepository
) : BaseViewModel() {

}