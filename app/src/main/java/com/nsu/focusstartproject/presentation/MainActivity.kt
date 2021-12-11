package com.nsu.focusstartproject.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.nsu.focusstartproject.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.checkExistsApprovedLoans()
        viewModel.checkIsDarkMode()
        viewModel.enableDarkModeEvent.observe(this) {
            if (it) {
                enableDarkMode()
            } else {
                enableLightMode()
            }
        }
    }

    private fun enableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    private fun enableLightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onViewResume()
    }

    override fun onStop() {
        super.onStop()
        viewModel.onViewStop()
    }
}