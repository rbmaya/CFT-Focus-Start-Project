package com.nsu.focusstartproject.presentation.no_auth_user_screens.authorization

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nsu.focusstartproject.R
import com.nsu.focusstartproject.databinding.AuthFragmentBinding
import com.nsu.focusstartproject.presentation.NoAuthUserFragment
import com.nsu.focusstartproject.utils.DataStatus
import com.nsu.focusstartproject.utils.errors_processing.ErrorCodeProcessor
import com.nsu.focusstartproject.utils.fields_processing.FieldsError
import com.nsu.focusstartproject.utils.view_actions.Animations.wiggle
import com.nsu.focusstartproject.utils.view_actions.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthenticationFragment : Fragment(R.layout.auth_fragment) {

    private val viewModel: AuthenticationViewModel by viewModels()
    private val binding: AuthFragmentBinding by viewBinding()

    @Inject
    lateinit var errorCodeProcessor: ErrorCodeProcessor

    companion object {
        const val WIGGLE_FIELD_TIME = 200L
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.checkIsAuthorized()

        binding.apply {
            signInButton.setOnClickListener {
                this@AuthenticationFragment.hideKeyboard()
                viewModel.signIn(
                    userName = username.text.toString(),
                    password = password.text.toString()
                )
            }
            registerButton.setOnClickListener {
                viewModel.onRegisterButtonClicked()
            }
        }
        initObservers()
    }

    private fun initObservers() {
        viewModel.authenticationStatus.observe(viewLifecycleOwner) {
            processAuthenticationState(it)
        }
        viewModel.wrongFieldsEvent.observe(viewLifecycleOwner) {
            processFieldsError(it)
        }
        viewModel.navigateToMainScreen.observe(viewLifecycleOwner) {
            navigateToAuthUserFragment()
        }
        viewModel.navigateToSignUpScreen.observe(viewLifecycleOwner) {
            navigateToRegistrationFragment()
        }
        viewModel.navigateToOnboardingScreen.observe(viewLifecycleOwner) {
            navigateToOnboardingFragment()
        }
    }

    private fun processAuthenticationState(dataStatus: DataStatus<Any>) {
        when (dataStatus) {
            is DataStatus.Loading -> {
                binding.authorizationProgressBar.visibility = View.VISIBLE
            }
            is DataStatus.Success -> {
                binding.authorizationProgressBar.visibility = View.INVISIBLE
                viewModel.onSuccessSignIn()
            }
            is DataStatus.Error -> {
                binding.authorizationProgressBar.visibility = View.INVISIBLE
                dataStatus.code?.let {
                    val message = errorCodeProcessor.processErrorCode(it)
                    showMessage(message)
                }
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun processFieldsError(fieldsError: FieldsError) {
        when (fieldsError) {
            FieldsError.EMPTY_USERNAME -> {
                binding.username.wiggle(WIGGLE_FIELD_TIME)
            }
            FieldsError.EMPTY_PASSWORD -> {
                binding.password.wiggle(WIGGLE_FIELD_TIME)
            }
            else -> {}
        }
    }

    private fun navigateToAuthUserFragment() {
        (requireParentFragment().requireParentFragment() as NoAuthUserFragment).navigateToAuthUserFragment()
    }

    private fun navigateToOnboardingFragment() {
        findNavController().navigate(R.id.action_authFragment_to_onboardingFragment)
    }

    private fun navigateToRegistrationFragment() {
        findNavController().navigate(R.id.action_authFragment_to_registrationFragment)
    }
}