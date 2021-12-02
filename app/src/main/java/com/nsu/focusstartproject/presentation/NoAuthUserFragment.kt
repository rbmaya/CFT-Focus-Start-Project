package com.nsu.focusstartproject.presentation

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nsu.focusstartproject.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoAuthUserFragment: Fragment(R.layout.no_auth_user_fragment) {

    fun navigateToAuthUserFragment(){
        findNavController().navigate(R.id.action_noAuthUserFragment_to_authUserFragment)
    }
}