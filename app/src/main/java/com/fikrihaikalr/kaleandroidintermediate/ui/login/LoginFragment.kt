package com.fikrihaikalr.kaleandroidintermediate.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fikrihaikalr.kaleandroidintermediate.R
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.auth.LoginBody
import com.fikrihaikalr.kaleandroidintermediate.databinding.FragmentLoginBinding
import com.fikrihaikalr.kaleandroidintermediate.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toSignUp()
        signIn()
        playAnimation()
    }

    private fun playAnimation() {
        val signIn = ObjectAnimator.ofFloat(binding.tvSignIn,View.ALPHA,1f).setDuration(100)
        val dontHaveAccount = ObjectAnimator.ofFloat(binding.tvDha,View.ALPHA,1f).setDuration(100)
        val signUp = ObjectAnimator.ofFloat(binding.tvSignUp,View.ALPHA,1f).setDuration(100)
        val email = ObjectAnimator.ofFloat(binding.tilEmail, View.ALPHA, 1f).setDuration(100)
        val password = ObjectAnimator.ofFloat(binding.tilPassword, View.ALPHA, 1f).setDuration(100)
        val btn = ObjectAnimator.ofFloat(binding.btnSignIn, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(signIn,dontHaveAccount,signUp,email,password,btn)
            start()
        }
    }

    private fun signIn() {
        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            if (validateInput()){
                loginViewModel.login(LoginBody(email, password))
                observeResponse()
            }
        }
    }

    private fun observeResponse() {
        loginViewModel.login.observe(viewLifecycleOwner) {
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
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        var flag = true
        binding.apply {
            if (etEmail.text.toString().isEmpty()) {
                flag = false
                tilEmail.error = getString(R.string.email_empty)
                etEmail.requestFocus()
            } else if (etPassword.text.toString().isEmpty()) {
                flag = false
                tilPassword.error = getString(R.string.password_empty)
                etPassword.requestFocus()
            }
            if (tilEmail.isErrorEnabled) {
                flag = false
            } else if (tilPassword.isErrorEnabled) {
                flag = false
            }
        }

        return flag
    }

    private fun toSignUp() {
        binding.tvSignUp.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}