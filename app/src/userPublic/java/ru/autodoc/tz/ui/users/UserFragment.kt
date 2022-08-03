package ru.autodoc.tz.ui.users

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.autodoc.tz.R
import ru.autodoc.tz.base.BaseFragment
import ru.autodoc.tz.data.model.User
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

        viewModel.getUser(login = args.rep.owner.login)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.userData.collect {
                updateUI(user = it)
            }
        }
        showLoadingProgress()
    }

    private fun showLoadingProgress() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.loading.collect {
                binding.progressBar.isVisible = it
                binding.content.isVisible = !it
            }
        }
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

    private fun updateUI(user: User?) {
        user?.apply {
            setBIO(bio = bio)
            setFollow(following = following, followers = followers)
            setBlog(blog = blog)
            setHtmlUrl(htmlUrl = htmlUrl)
            setTwitterUserName(twitterUserName = twitterUserName)
        }
    }

    private fun setBIO(bio: String?) {
        bio?.let {
            binding.bio.text = getString(R.string.bio_s, it)
        }
    }

    private fun setFollow(following: Int, followers: Int) {
        binding.follow.text = getString(
            R.string.follow_s_s,
            following.toString(),
            followers.toString()
        )
    }

    private fun setBlog(blog: String?) {
        blog?.let {
            if (it != "") {
                binding.blog.paintFlags = binding.blog.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                binding.blog.text = it
                clickUri(uri = it, view = binding.blog)
            } else {
                disableView(viewTitle = binding.blogTitle, view = binding.blog)
            }
        } ?: run {
            disableView(viewTitle = binding.blogTitle, view = binding.blog)
        }
    }

    private fun setHtmlUrl(htmlUrl: String?) {
        htmlUrl?.let {
            binding.htmlUrl.paintFlags = binding.htmlUrl.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            binding.htmlUrl.text = it
            clickUri(uri = it, view = binding.htmlUrl)
        } ?: run {
            disableView(viewTitle = binding.htmlUrlTitle, view = binding.htmlUrl)
        }
    }

    private fun clickUri(uri: String, view: View) {
        view.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(uri)
            startActivity(intent)
        }
    }

    private fun setTwitterUserName(twitterUserName: String?) {
        twitterUserName?.let {
            binding.twitter.text = it
        } ?: run {
            disableView(viewTitle = binding.twitterTitle, view = binding.twitter)
        }
    }

    private fun disableView(viewTitle: View, view: View) {
        viewTitle.isVisible = false
        view.isVisible = false
    }

    override fun onError(error: String) {
        Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
    }
}