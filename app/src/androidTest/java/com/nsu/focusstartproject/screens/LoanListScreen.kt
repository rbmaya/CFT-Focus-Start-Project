package com.nsu.focusstartproject.screens

import android.view.View
import com.nsu.focusstartproject.R
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object LoanListScreen : Screen<LoanListScreen>() {

    val recyclerView = KRecyclerView(
        builder = { withId(R.id.loan_list) },
        itemTypeBuilder = { itemType(::LoanItem) }
    )

    class LoanItem(parent: Matcher<View>) : KRecyclerItem<LoanItem>(parent) {
        val amount = KTextView(parent) { withId(R.id.amount) }

        val date = KTextView(parent) { withId(R.id.date) }
    }

    val addLoanButton = KButton { withId(R.id.add_loan_button) }

}