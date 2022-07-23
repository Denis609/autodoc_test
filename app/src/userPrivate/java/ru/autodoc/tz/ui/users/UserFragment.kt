package ru.autodoc.tz.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.autodoc.tz.base.BaseFragment
import ru.autodoc.tz.databinding.UserFragmentBinding
import ru.autodoc.tz.utils.ImageLoader

@AndroidEntryPoint
class UserFragment : BaseFragment() {
    private lateinit var binding: UserFragmentBinding
    override val viewModel: UserViewModel by viewModels()
    private val args: UserFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UserFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }
        setAvatar(url = args.rep.owner.avatarUrl)
        setUserName(userName = args.rep.owner.login)
    }

    private fun setAvatar(url: String?) {
        ImageLoader.picasso(
            url = url,
            binding.avatar
        )
    }

    private fun setUserName(userName: String) {
        binding.userName.text = userName
    }

    override fun onError(error: String) {
        Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
    }
}