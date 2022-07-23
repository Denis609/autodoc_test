package ru.autodoc.tz.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

abstract class BaseFragment : Fragment() {
    abstract val viewModel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.errorFlow.collect {
                onError(it)
            }
        }
    }

    abstract fun onError(error: String)
}