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
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.autodoc.tz.R
import ru.autodoc.tz.data.model.Rep
import ru.autodoc.tz.databinding.RepsFragmentBinding
import ru.autodoc.tz.ui.reps.adapter.RepsAdapter
import ru.autodoc.tz.utils.PagingLoadStateAdapter
import java.lang.Exception
import javax.inject.Inject


@AndroidEntryPoint
class RepsFragment : Fragment(), RepsAdapter.RepsClickListener {
    private lateinit var binding: RepsFragmentBinding
    private val viewModel: RepsViewModel by viewModels()

    @Inject
    lateinit var repsAdapter: RepsAdapter

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
        binding.apply {
            items.layoutManager = LinearLayoutManager(requireContext())
            items.adapter = repsAdapter
            repsAdapter.apply {
                repsClickListener = this@RepsFragment
                refreshLayout.setOnRefreshListener {
                    refresh()
                }

                items.adapter = withLoadStateFooter(
                    PagingLoadStateAdapter(this)
                )

                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    loadStateFlow.collect {
                        refreshLayout.isRefreshing = it.refresh is LoadState.Loading
                    }
                }

                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    viewModel.reps.collect {
                        repsAdapter.submitData(it)
                    }
                }
            }
        }

    }

    private fun toolbarInit() {
        binding.apply {
            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    viewLifecycleOwner.lifecycleScope.launch {
                        repsAdapter.submitData(PagingData.empty())
                        viewModel.getReps(query)
                    }
                    globalQuery = query
                    search.clearFocus()
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    globalQuery = newText
                    return false
                }
            })

            if (globalQuery != "") {
                search.setQuery(globalQuery, false)
                title.isVisible = false
                titleMini.isVisible = true
            } else {
                title.isVisible = true
            }

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
            title.setOnClickListener {
                scrollToTop()
            }
            titleMini.setOnClickListener {
                scrollToTop()
            }
        }
    }

    private fun scrollToTop() {
        binding.apply {
            items.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(rv: RecyclerView, state: Int) {
                    if (state == RecyclerView.SCROLL_STATE_IDLE) {
                        repsAdapter.notifyItemInserted(0)
                        rv.removeOnScrollListener(this)
                    }
                }
            })
            items.smoothScrollToPosition(0)
        }
    }

    override fun onRepsClicked(rep: Rep) {
        try {
            findNavController().navigate(RepsFragmentDirections.actionHomeToUser(rep))
        } catch (e: Exception) { }
    }
}