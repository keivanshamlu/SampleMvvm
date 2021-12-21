package com.shamlou.featureone.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.shamlou.core.assisted.ViewModelFactory
import com.shamlou.featureone.ui.posts.posts.AdapterPosts
import com.shamlou.featureone.databinding.FragmentFeatureOneBinding
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentFeatureOne: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val adapterPosts = AdapterPosts()
    private var _binding : FragmentFeatureOneBinding? = null
    private val binding get() = _binding
    private val viewModel by viewModels<FeatureOneViewModel> {
        viewModelFactory.create(this, arguments)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeatureOneBinding.inflate(inflater , container , false)
        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.viewModel = viewModel
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.recyclerviewPosts?.apply {
            adapter = adapterPosts
            layoutManager = LinearLayoutManager(context)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.posts.collect {

                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}