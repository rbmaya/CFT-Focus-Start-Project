package com.nsu.focusstartproject.screens

import com.nsu.focusstartproject.R
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

object NewLoanScreen : Screen<NewLoanScreen>() {

    val conditions = KTextView { withId(R.id.conditions) }

    val amount = KTextView { withId(R.id.amount) }

    val firstName = KTextView { withId(R.id.first_name) }

    val lastName = KTextView { withId(R.id.last_name) }

    val phoneNumber = KTextView { withId(R.id.phone_number) }

    val addLoanButton = KButton { withId(R.id.add_loan) }
}