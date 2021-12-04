package com.nsu.focusstartproject.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nsu.focusstartproject.R
import com.nsu.focusstartproject.databinding.AuthUserFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthUserFragment: Fragment(R.layout.auth_user_fragment) {
    private val binding: AuthUserFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
    }

    private fun setToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.mainToolbar)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.loanListFragment))

        val navHost =
            childFragmentManager.findFragmentById(R.id.auth_user_container_fragment) as NavHostFragment
        val navController = navHost.navController

        binding.mainToolbar.setupWithNavController(navController, appBarConfiguration)
    }
}