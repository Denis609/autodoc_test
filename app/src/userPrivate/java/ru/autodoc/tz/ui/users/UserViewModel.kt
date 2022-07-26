package ru.autodoc.tz.ui.users

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.autodoc.tz.base.BaseViewModel
import ru.autodoc.tz.domain.user.UserGetUseCase
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userGetUseCase: UserGetUseCase
) : BaseViewModel() {

}