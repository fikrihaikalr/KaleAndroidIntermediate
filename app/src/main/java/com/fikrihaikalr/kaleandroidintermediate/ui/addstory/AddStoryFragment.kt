package com.fikrihaikalr.kaleandroidintermediate.ui.addstory

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fikrihaikalr.kaleandroidintermediate.R
import com.fikrihaikalr.kaleandroidintermediate.databinding.FragmentAddStoryBinding
import com.fikrihaikalr.kaleandroidintermediate.utils.Constants
import com.fikrihaikalr.kaleandroidintermediate.utils.createCustomTempFile
import com.fikrihaikalr.kaleandroidintermediate.utils.imageMultipart
import com.fikrihaikalr.kaleandroidintermediate.utils.stringToRequestBody
import com.fikrihaikalr.kaleandroidintermediate.utils.uriToFile
import com.fikrihaikalr.kaleandroidintermediate.wrapper.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class AddStoryFragment : Fragment() {

    private var _binding: FragmentAddStoryBinding? = null
    private val binding get() = _binding!!
    private val addStoryViewModel: AddStoryViewModel by activityViewModels()
    private var getFile: File? = null
    private lateinit var pathPhoto: String

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val myFile = File(pathPhoto)
            getFile = myFile

            val result = BitmapFactory.decodeFile(getFile?.path)
            binding.image.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val selectedImg: Uri = it.data?.data as Uri
            val myFile = uriToFile(selectedImg, requireContext())

            getFile = myFile
            binding.image.setImageURI(selectedImg)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddStoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back()
        chooseImageUpload()
        uploadStory()
    }

    private fun observeData() {
        addStoryViewModel.addStory.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.animationLoading.isVisible = true
                }
                is Resource.Error -> {
                    binding.animationLoading.isVisible = false
                }
                is Resource.Success -> {
                    binding.animationLoading.isVisible = false
                    view?.let { view ->
                        showSnackBar(view, it.data?.message.toString())
                    }
                    binding.image.setImageResource(R.drawable.baseline_image_40)
                    binding.etDescription.text?.clear()
                    findNavController().navigate(R.id.homeFragment)
                }
                is Resource.Empty -> {
                    binding.animationLoading.isVisible = false
                }
            }
        }
    }

    private fun chooseImageUpload() {
        binding.apply {
            btnCamera.setOnClickListener { launchCamera() }
            btnGallery.setOnClickListener { launchGallery() }
        }
    }

    private fun uploadStory() {
        binding.btnUpload.setOnClickListener {
            if (validateInput()) {
                val description = binding.etDescription.text.toString().trim()
                addStoryViewModel.getToken().observe(viewLifecycleOwner) {
                    getFile?.let { getFile -> imageMultipart(getFile) }?.let { fileMultipart ->
                        addStoryViewModel.addNewStory(
                            it,
                            fileMultipart, stringToRequestBody(description)
                        )
                        observeData()
                    }
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        var flag = true
        binding.apply {
            if (getFile == null) {
                flag = false
                view?.let {
                    showSnackBar(it, "Upload your photo first!")
                }
            } else if (etDescription.text.toString().isEmpty()) {
                flag = false
                tilDescription.error = getString(R.string.description_empty)
                etDescription.requestFocus()
            }
        }
        return flag
    }


    private fun back() {
        binding.icBackBtn.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    private fun launchGallery() {
        val intent = Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            type = "image/*"
        }
        val chooser = Intent.createChooser(intent, "Choose a picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun launchCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)

        createCustomTempFile(requireActivity().application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                Constants.MY_PACKAGE,
                it
            )
            pathPhoto = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
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