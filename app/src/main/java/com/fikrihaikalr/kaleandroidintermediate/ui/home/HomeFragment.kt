package com.fikrihaikalr.kaleandroidintermediate.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fikrihaikalr.kaleandroidintermediate.R
import com.fikrihaikalr.kaleandroidintermediate.databinding.FragmentHomeBinding
import com.fikrihaikalr.kaleandroidintermediate.ui.home.adapter.StoryAdapter
import com.fikrihaikalr.kaleandroidintermediate.wrapper.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var storiesAdapter: StoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        observeData()
        toSettings()
        toAddStory()
    }

    private fun toAddStory() {
        binding.fabAddStory.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_addStoryFragment)
        }
    }

    private fun toSettings() {
        binding.btnSettings.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
        }
    }

    private fun observeData() {
        homeViewModel.getToken().observe(viewLifecycleOwner){ token ->
            homeViewModel.getAllStories(token)
        }
        homeViewModel.stories.observe(viewLifecycleOwner){
            when (it) {
                is Resource.Loading -> {
                    binding.apply {
                        animationLoading.isVisible = true
                        rvStory.isVisible = false
                    }
                }
                is Resource.Error -> {
                    binding.apply {
                        animationLoading.isVisible = false
                        rvStory.isVisible = false
                    }
                    view?.let { view -> showSnackBar(view, it.message.toString()) }
                }
                is Resource.Success -> {
                    binding.apply {
                        animationLoading.isVisible = false
                        rvStory.isVisible = true
                    }
                    storiesAdapter.differ.submitList(it.data?.listStory)
                }
                is Resource.Empty -> {
                    binding.apply {
                        animationLoading.isVisible = false
                        rvStory.isVisible = false
                    }
                }
            }
        }
    }

    private fun showSnackBar(view: View, msg: String) {
        Snackbar.make(view,msg,Snackbar.LENGTH_SHORT).show()
    }

    private fun setupUi() {
        storiesAdapter = StoryAdapter()
        binding.rvStory.apply {
            adapter = storiesAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
        }
    }

    private fun refreshStories() {
        homeViewModel.getToken().observe(viewLifecycleOwner){token ->
            homeViewModel.getAllStories(token)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshStories()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}