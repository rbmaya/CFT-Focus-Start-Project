package com.nsu.focusstartproject.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayout
import com.nsu.focusstartproject.R
import com.nsu.focusstartproject.databinding.AuthUserFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthUserFragment : Fragment(R.layout.auth_user_fragment) {
    private val binding: AuthUserFragmentBinding by viewBinding()

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNavController()
        initTabListener()
        initSelectedFragment()
    }

    private fun initSelectedFragment() {
        when (navController.currentBackStackEntry?.destination?.label) {
            requireContext().getString(R.string.loans) -> {
                binding.tabLayout.getTabAt(0)?.select()
            }
            requireContext().getString(R.string.account) -> {
                binding.tabLayout.getTabAt(1)?.select()
            }
        }
    }

    private fun initNavController() {
        val navHost =
            childFragmentManager.findFragmentById(R.id.auth_user_container_fragment) as NavHostFragment
        navController = navHost.navController
    }

    private fun initTabListener() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    when (it.position) {
                        0 -> {
                            navigateToLoanListFragment()
                        }
                        1 -> {
                            navigateToAccountSettingsFragment()
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun navigateToLoanListFragment() {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_right)
            .build()

        clearBackStack()
        navController.navigate(R.id.loanListFragment, null, navOptions)
    }

    private fun navigateToAccountSettingsFragment() {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_left)
            .setExitAnim(R.anim.slide_out_left)
            .build()

        clearBackStack()

        navController.navigate(R.id.accountSettingsFragment, null, navOptions)
    }

    fun navigateToNoAuthUserFragment() {
        findNavController().navigate(R.id.action_authUserFragment_to_noAuthUserFragment)
    }

    private fun clearBackStack() {
        while (navController.currentDestination != null) {
            navController.popBackStack()
        }
    }

}