package com.nsu.focusstartproject.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.nsu.focusstartproject.R
import com.nsu.focusstartproject.databinding.OnboardingFragmentBinding
import com.nsu.focusstartproject.presentation.NoAuthUserFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment : Fragment(R.layout.onboarding_fragment) {
    private val viewModel: OnboardingViewModel by viewModels()
    private val binding: OnboardingFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.apply {
            viewpager.adapter = ViewPagerAdapter(resources, childFragmentManager, lifecycle)
            TabLayoutMediator(tabLayout, viewpager) { tab, position ->
                tab.text = getString(R.string.page_number, (position + 1).toString())
            }.attach()
            skipButton.setOnClickListener {
                viewModel.onSkipButtonClicked()
            }
        }
    }

    private fun initObservers() {
        viewModel.navigateToMainScreen.observe(viewLifecycleOwner) {
            navigateToAuthUserFragment()
        }
    }

    private fun navigateToAuthUserFragment() {
        (requireParentFragment().requireParentFragment() as NoAuthUserFragment).navigateToAuthUserFragment()
    }

}