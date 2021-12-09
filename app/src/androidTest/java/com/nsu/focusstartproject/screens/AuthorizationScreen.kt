package com.nsu.focusstartproject.screens

import com.nsu.focusstartproject.R
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

object AuthorizationScreen : Screen<AuthorizationScreen>() {

    val username = KEditText { withId(R.id.username) }

    val password = KEditText { withId(R.id.password) }

    val registerButton = KTextView { withId(R.id.register_button) }

    val signInButton = KButton { withId(R.id.sign_in_button) }

}