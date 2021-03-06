package com.nsu.focusstartproject.presentation.auth_user_screens.account_settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nsu.focusstartproject.R
import com.nsu.focusstartproject.databinding.AccountSettingsFragmentBinding
import com.nsu.focusstartproject.presentation.AuthUserFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountSettingsFragment : Fragment(R.layout.account_settings_fragment) {

    private val viewModel: AccountSettingsViewModel by viewModels()
    private val binding: AccountSettingsFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initObservers()
        viewModel.initSwitchDarkMode()
    }

    private fun initObservers() {
        viewModel.signOutEvent.observe(viewLifecycleOwner) {
            showAlertDialog()
        }
        viewModel.navigateToAuthFragment.observe(viewLifecycleOwner) {
            navigateToAuthFragment()
        }
        viewModel.enableDarkMode.observe(viewLifecycleOwner) {
            binding.darkModeSwitch.isChecked = it
            if (it) {
                enableDarkMode()
            } else {
                enableLightMode()
            }
        }
    }

    private fun initListeners() {
        binding.signOutButton.setOnClickListener {
            viewModel.onSignOutButtonClicked()
        }
        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onSwitchDarkMode(isChecked = isChecked)
        }
    }

    private fun navigateToAuthFragment() {
        (requireParentFragment().requireParentFragment() as AuthUserFragment).navigateToNoAuthUserFragment()
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(requireContext())
            .setIcon(R.drawable.ic_exit)
            .setTitle(getString(R.string.are_you_sure))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.onSignOutConfirm()
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun enableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    private fun enableLightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

}