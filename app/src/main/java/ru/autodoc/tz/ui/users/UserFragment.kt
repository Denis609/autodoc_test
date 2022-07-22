package ru.autodoc.tz.ui.users

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import ru.autodoc.tz.R
import ru.autodoc.tz.data.model.User
import ru.autodoc.tz.databinding.UserFragmentBinding

@AndroidEntryPoint
class UserFragment : Fragment() {
    private lateinit var binding: UserFragmentBinding
    private val viewModel: UserViewModel by viewModels()
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
        viewModel.getUser(args.reps.owner.login)
        binding.apply {
            backIcon.setOnClickListener {
                findNavController().popBackStack()
            }

            Picasso
                .get()
                .load(args.reps.owner.avatar_url)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_account_circle_24)
                .into(avatar)
            userName.text = args.reps.owner.login
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.userData.collect {
                updateUI(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.loading.collect {
                binding.progressBar.isVisible = it
                binding.content.isVisible = !it
            }
        }
    }

    private fun updateUI(user: User?) {
        user?.let {
            binding.apply {
                it.bio?.let { bio.text = getString(R.string.bio_s, it) }
                follow.text = getString(
                    R.string.follow_s_s,
                    it.following,
                    it.followers
                )
                it.blog?.let {
                    if (it != "") {
                        blog.paintFlags = blog.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                        blog.text = it
                        blog.setOnClickListener { _ ->
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(it)
                            startActivity(intent)
                        }
                    } else {
                        disableView(blogTitle, blog)
                    }
                } ?: run {
                    disableView(blogTitle, blog)
                }
                it.html_url?.let {
                    gitHub.paintFlags = blog.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                    gitHub.text = it

                    gitHub.setOnClickListener { _ ->
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(it)
                        startActivity(intent)
                    }

                } ?: run {
                    disableView(gitHubTitle, gitHub)
                }
                it.twitter_username?.let { twitter.text = it } ?: run {
                    disableView(
                        twitterTitle,
                        twitter
                    )
                }
            }
        }
    }

    private fun disableView(viewTitle: View, view: View) {
        viewTitle.isVisible = false
        view.isVisible = false
    }
}