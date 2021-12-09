package com.nsu.focusstartproject.screens

import com.nsu.focusstartproject.R
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton

object RegistrationScreen : Screen<RegistrationScreen>() {

    val username = KEditText { withId(R.id.username) }

    val password = KEditText { withId(R.id.password) }

    val signUpButton = KButton { withId(R.id.sign_up_button) }
}