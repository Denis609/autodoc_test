package ru.autodoc.tz.ui.home

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
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.autodoc.tz.R
import ru.autodoc.tz.databinding.RepsFragmentBinding
import ru.autodoc.tz.ui.home.adapter.RepsAdapter
import javax.inject.Inject


@AndroidEntryPoint
class RepsFragment : Fragment() {
    private lateinit var binding: RepsFragmentBinding
    private val viewModel: RepsViewModel by viewModels()

    @Inject
    lateinit var repsAdapter: RepsAdapter

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

        binding.items.layoutManager = LinearLayoutManager(requireContext())
        binding.items.adapter = repsAdapter
        with(repsAdapter) {

            binding.refreshLayout.setOnRefreshListener {
                refresh()
            }
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                loadStateFlow.collect {
                    binding.refreshLayout.isRefreshing = it.refresh is LoadState.Loading
                }
            }

            binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.getReps(query)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })

            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.reps.collect {
                    repsAdapter.submitData(it)
                }
            }
        }






        toolbarInit()
    }

    private fun toolbarInit() {
        binding.title.isVisible = binding.search.isIconified
        binding.search.setOnQueryTextFocusChangeListener { _, b ->
            binding.title.isVisible = !b
            binding.titleMini.isVisible = b
            if (b) {
                binding.titleMini.animation = AnimationUtils.loadAnimation(requireContext(), R.anim.up)
            } else {
                binding.title.animation = AnimationUtils.loadAnimation(requireContext(), R.anim.up)
            }
        }
    }
}