package com.nsu.focusstartproject.screens

import com.nsu.focusstartproject.R
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

object NewLoanScreen : Screen<NewLoanScreen>() {

    val conditions = KTextView { withId(R.id.conditions) }

    val addLoanButton = KButton { withId(R.id.add_loan) }
}