package ru.autodoc.tz.ui.reps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.autodoc.tz.R
import ru.autodoc.tz.base.BaseFragment
import ru.autodoc.tz.data.model.Rep
import ru.autodoc.tz.databinding.RepsFragmentBinding
import ru.autodoc.tz.utils.PagingLoadStateAdapter


@AndroidEntryPoint
class RepsFragment : BaseFragment() {

    private lateinit var binding: RepsFragmentBinding
    override val viewModel: RepsViewModel by viewModels()

    private val repsAdapter: RepsAdapter = RepsAdapter(
        repsClickListener = repsOnClickListener()
    )

    private var globalQuery: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RepsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterInit()
        toolbarInit()
    }

    private fun adapterInit() {
        binding.items.layoutManager = LinearLayoutManager(requireContext())
        binding.items.adapter = repsAdapter
        repsAdapter.apply {
            binding.refreshLayout.setOnRefreshListener { refresh() }
            binding.items.adapter = withLoadStateFooter(PagingLoadStateAdapter(this))

            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                loadStateFlow.collect {
                    binding.refreshLayout.isRefreshing = it.refresh is LoadState.Loading
                }
            }

            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.reps.collect {
                    repsAdapter.submitData(pagingData = it)
                }
            }
        }
    }

    private fun toolbarInit() {
        queryFocusChangeListener()
        queryTextListener()
        isQueryNotEmpty()
        clickTitle()
    }

    private fun queryFocusChangeListener() {
        binding.apply {
            search.setOnQueryTextFocusChangeListener { _, b ->
                if (b) {
                    if (search.query.isEmpty()) {
                        title.isVisible = false
                        titleMini.isVisible = true
                        titleMini.animation =
                            AnimationUtils.loadAnimation(requireContext(), R.anim.up)
                    }
                } else {
                    if (search.query.isEmpty()) {
                        title.isVisible = true
                        titleMini.isVisible = false
                        search.isIconified = true
                        title.animation = AnimationUtils.loadAnimation(requireContext(), R.anim.up)
                    }
                }
            }
        }
    }

    private fun queryTextListener() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewLifecycleOwner.lifecycleScope.launch {
                    repsAdapter.submitData(pagingData = PagingData.empty())
                    viewModel.getReps(query = query)
                }
                globalQuery = query
                binding.search.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                globalQuery = newText
                return false
            }
        })
    }

    private fun isQueryNotEmpty() {
        if (globalQuery != "") {
            binding.search.setQuery(globalQuery, false)
            binding.title.isVisible = false
            binding.titleMini.isVisible = true
        } else {
            binding.title.isVisible = true
        }
    }

    private fun clickTitle() {
        binding.title.setOnClickListener {
            scrollToTop()
        }
        binding.titleMini.setOnClickListener {
            scrollToTop()
        }
    }

    private fun scrollToTop() {
        binding.items.scrollToPosition(0)
    }

    private fun repsOnClickListener(): (Rep) -> Unit {
        return { rep: Rep ->
            try {
                findNavController().navigate(RepsFragmentDirections.actionHomeToUser(rep = rep))
            } catch (e: Exception) {
            }
        }
    }

    override fun onError(error: String) {
        Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
    }
}