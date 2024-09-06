package com.fikrihaikalr.kaleandroidintermediate.ui.splashscreen

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fikrihaikalr.kaleandroidintermediate.R
import com.fikrihaikalr.kaleandroidintermediate.databinding.FragmentSplashScreenBinding
import com.fikrihaikalr.kaleandroidintermediate.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenFragment : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!
    private val splashScreenViewModel: SplashScreenViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(inflater,container,false)
        darkMode()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playAnimation()
    }

    private fun playAnimation() {
        val appName = ObjectAnimator.ofFloat(binding.tvAppName, View.ALPHA,1f).setDuration(1000)
        val together = AnimatorSet().apply {
            playTogether(appName)
        }
        AnimatorSet().apply {
            playSequentially(appName,together)
            start()
        }
    }

    private fun darkMode() {
        splashScreenViewModel.getTheme().observe(viewLifecycleOwner){ isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        Handler().postDelayed({
            lifecycleScope.launchWhenCreated {
                session()
            }
        },Constants.LOADING_TIME)
    }

    private fun session() {
        splashScreenViewModel.getToken().observe(viewLifecycleOwner){
            if (it.isEmpty()){
                findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
            }else{
                findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}