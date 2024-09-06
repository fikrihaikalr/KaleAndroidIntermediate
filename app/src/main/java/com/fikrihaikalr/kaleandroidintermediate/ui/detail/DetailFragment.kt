package com.fikrihaikalr.kaleandroidintermediate.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.story.Story
import com.fikrihaikalr.kaleandroidintermediate.databinding.FragmentDetailBinding
import com.fikrihaikalr.kaleandroidintermediate.wrapper.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by activityViewModels()
    private val navArgs: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        backOnPressed()
    }

    private fun backOnPressed() {
        binding.icBackBtn.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    private fun observeData() {
        detailViewModel.getToken().observe(viewLifecycleOwner){
            detailViewModel.getDetailStory(it, navArgs.id)
        }
        detailViewModel.detailStory.observe(viewLifecycleOwner){
            when (it) {
                is Resource.Loading -> {
                    binding.animationLoading.isVisible = true
                }
                is Resource.Error -> {
                    binding.animationLoading.isVisible = false
                    view?.let { view -> showSnackBar(view, it.message.toString()) }
                }
                is Resource.Success -> {
                    binding.animationLoading.isVisible = false
                    it.data?.story?.let { story -> bindView(story) }
                }
                is Resource.Empty -> {
                    binding.animationLoading.isVisible = false
                }
            }
        }
    }

    private fun bindView(story: Story) {
        binding.apply {
            Glide.with(requireContext())
                .load(story.photoUrl)
                .into(backdrop)
            tvName.text = story.name
            tvDetailDescription.text = story.description
            tvDate.text = story.createdAt?.substring(0,10)
            tvClock.text = story.createdAt?.substring(11,16)
        }
    }

    private fun showSnackBar(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}