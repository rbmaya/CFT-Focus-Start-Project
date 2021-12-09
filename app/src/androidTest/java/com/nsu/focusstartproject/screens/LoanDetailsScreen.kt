package com.nsu.focusstartproject.screens

import com.nsu.focusstartproject.R
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KTextView

object LoanDetailsScreen : Screen<LoanDetailsScreen>() {

    val amount = KTextView { withId(R.id.amount) }

    val date = KTextView { withId(R.id.date) }

    val firstName = KTextView { withId(R.id.first_name) }

    val lastName = KTextView { withId(R.id.last_name) }

    val percent = KTextView { withId(R.id.percent) }

    val state = KTextView { withId(R.id.state) }
}