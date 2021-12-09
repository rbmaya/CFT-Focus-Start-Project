package com.nsu.focusstartproject.screens

import com.nsu.focusstartproject.R
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton

object OnboardingScreen : Screen<OnboardingScreen>() {

    val skipButton = KButton { withId(R.id.skip_button) }
}