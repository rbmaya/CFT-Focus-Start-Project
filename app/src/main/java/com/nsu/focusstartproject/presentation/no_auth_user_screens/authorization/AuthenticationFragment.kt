package com.nsu.focusstartproject.presentation.no_auth_user_screens.authorization

import android.app.Activity
import android.content.Context
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
import com.nsu.focusstartproject.utils.Animations.wiggle
import com.nsu.focusstartproject.utils.DataStatus
import com.nsu.focusstartproject.utils.ErrorCode
import com.nsu.focusstartproject.utils.FieldsError
import com.nsu.focusstartproject.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationFragment : Fragment(R.layout.auth_fragment) {

    private val viewModel: AuthenticationViewModel by viewModels()
    private val binding: AuthFragmentBinding by viewBinding()

    companion object {
        const val WIGGLE_FIELD_TIME = 500L
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

    private fun initObservers(){
        viewModel.authenticationStatus.observe(viewLifecycleOwner){
            processAuthenticationState(it)
        }
        viewModel.wrongFieldsEvent.observe(viewLifecycleOwner){
            processFieldsError(it)
        }
        viewModel.navigateToMainScreen.observe(viewLifecycleOwner){
            navigateToAuthUserFragment()
        }
        viewModel.navigateToSignUpScreen.observe(viewLifecycleOwner){
            navigateToRegistrationFragment()
        }
    }

    private fun processAuthenticationState(dataStatus: DataStatus<Any>){
        when (dataStatus){
            is DataStatus.Loading -> {
                binding.authorizationProgressBar.visibility = View.VISIBLE
            }
            is DataStatus.Success -> {
                binding.authorizationProgressBar.visibility = View.INVISIBLE
                viewModel.onSuccessSignIn()
            }
            is DataStatus.Error -> {
                binding.authorizationProgressBar.visibility = View.INVISIBLE
                dataStatus.code?.let { processErrorCode(it) }
            }
        }
    }

    private fun processErrorCode(errorCode: ErrorCode){
        when (errorCode){
            ErrorCode.UNAUTHORIZED -> {
                showMessage(getString(R.string.unauthorized_error_body))
            }
            ErrorCode.FORBIDDEN -> {
                showMessage(getString(R.string.forbidden_error_body))
            }
            ErrorCode.NOT_FOUND -> {
                showMessage(getString(R.string.not_found_error_body))
            }
            ErrorCode.UNKNOWN -> {
                showMessage(getString(R.string.unknown_error_body))
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
        }
    }

    private fun navigateToAuthUserFragment(){
        (requireParentFragment().requireParentFragment() as NoAuthUserFragment).navigateToAuthUserFragment()
    }

    private fun navigateToRegistrationFragment(){
        findNavController().navigate(R.id.action_authFragment_to_registrationFragment)
    }
}