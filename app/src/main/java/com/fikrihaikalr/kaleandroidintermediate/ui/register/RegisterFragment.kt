package com.fikrihaikalr.kaleandroidintermediate.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fikrihaikalr.kaleandroidintermediate.R
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.auth.RegisterBody
import com.fikrihaikalr.kaleandroidintermediate.databinding.FragmentRegisterBinding
import com.fikrihaikalr.kaleandroidintermediate.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val registerViewModel: RegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        onBackPressed()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toSignIn()
        signUp()
        playAnimation()
        observeResponse()
    }

    private fun observeResponse() {
        registerViewModel.register.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.animationLoading.isVisible = true
                }
                is Resource.Error -> {
                    binding.animationLoading.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Empty -> {
                    binding.animationLoading.isVisible = false
                }
                is Resource.Success -> {
                    binding.animationLoading.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        it.data?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.loginFragment)
                }
            }
        }
    }

    private fun signUp() {
        binding.btnSignUp.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            if (validateInput()) {
                registerViewModel.register(RegisterBody(username, email, password))
            }
        }
    }

    private fun validateInput(): Boolean {
        var flag = true
        binding.apply {
            if (etUsername.text.toString().isEmpty()) {
                flag = false
                tilUsername.error = getString(R.string.username_empty)
                etUsername.requestFocus()
            } else if (etEmail.text.toString().isEmpty()) {
                flag = false
                tilEmail.error = getString(R.string.email_empty)
                etEmail.requestFocus()
            } else if (etPassword.text.toString().isEmpty()) {
                flag = false
                tilPassword.error = getString(R.string.password_empty)
                etPassword.requestFocus()
            }
            if (tilUsername.isErrorEnabled) {
                flag = false
            } else if (tilEmail.isErrorEnabled) {
                flag = false
            } else if (tilPassword.isErrorEnabled) {
                flag = false
            }
        }
        return flag
    }

    private fun playAnimation() {
        val signUp = ObjectAnimator.ofFloat(binding.tvSignUp,View.ALPHA,1f).setDuration(100)
        val haveAccount = ObjectAnimator.ofFloat(binding.tvDha,View.ALPHA,1f).setDuration(100)
        val signIn = ObjectAnimator.ofFloat(binding.tvSignIn,View.ALPHA,1f).setDuration(100)
        val username = ObjectAnimator.ofFloat(binding.tilUsername, View.ALPHA, 1f).setDuration(100)
        val email = ObjectAnimator.ofFloat(binding.tilEmail, View.ALPHA, 1f).setDuration(100)
        val password = ObjectAnimator.ofFloat(binding.tilPassword, View.ALPHA, 1f).setDuration(100)
        val btn = ObjectAnimator.ofFloat(binding.btnSignUp, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(signUp,haveAccount,signIn,username, email, password, btn)
            start()
        }
    }

    private fun toSignIn() {
        binding.tvSignIn.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun onBackPressed() {
        val callbacks: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callbacks)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}