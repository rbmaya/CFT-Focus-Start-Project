package com.nsu.focusstartproject.presentation.no_auth_user_screens.registration

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nsu.focusstartproject.R
import com.nsu.focusstartproject.databinding.RegistrationFragmentBinding
import com.nsu.focusstartproject.utils.DataStatus
import com.nsu.focusstartproject.utils.errors_processing.ErrorCodeProcessor
import com.nsu.focusstartproject.utils.fields_processing.FieldsError
import com.nsu.focusstartproject.utils.view_actions.Animations.wiggle
import com.nsu.focusstartproject.utils.view_actions.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment : Fragment(R.layout.registration_fragment) {

    private val viewModel: RegistrationViewModel by viewModels()
    private val binding: RegistrationFragmentBinding by viewBinding()

    @Inject
    lateinit var errorCodeProcessor: ErrorCodeProcessor

    companion object {
        const val WIGGLE_FIELD_TIME = 500L
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            signUpButton.setOnClickListener {
                this@RegistrationFragment.hideKeyboard()
                viewModel.signUp(
                    userName = username.text.toString(),
                    password = password.text.toString()
                )
            }
        }
        initObservers()
    }

    private fun initObservers(){
        viewModel.registrationStatus.observe(viewLifecycleOwner) {
            processRegistrationState(it)
        }
        viewModel.wrongFieldsEvent.observe(viewLifecycleOwner){
            processFieldsError(it)
        }
        viewModel.navigateToAuthentication.observe(viewLifecycleOwner){
            navigateToAuthFragment()
        }
    }

    private fun processRegistrationState(dataStatus: DataStatus<Any>){
        when (dataStatus){
            is DataStatus.Loading -> {
                binding.authorizationProgressBar.visibility = View.VISIBLE
            }
            is DataStatus.Success -> {
                binding.authorizationProgressBar.visibility = View.INVISIBLE
                showMessage(getString(R.string.success_registration))
                viewModel.onSuccessSignUp()
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

    private fun showMessage(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun processFieldsError(fieldsError: FieldsError){
        when (fieldsError){
            FieldsError.EMPTY_USERNAME -> {
                binding.username.wiggle(WIGGLE_FIELD_TIME)
            }
            FieldsError.EMPTY_PASSWORD -> {
                binding.password.wiggle(WIGGLE_FIELD_TIME)
            }
            else -> {}
        }
    }

    private fun navigateToAuthFragment(){
        findNavController().navigateUp()
    }

}