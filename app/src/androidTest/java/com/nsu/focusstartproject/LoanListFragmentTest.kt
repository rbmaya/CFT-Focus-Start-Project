package com.nsu.focusstartproject

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nsu.focusstartproject.presentation.MainActivity
import com.nsu.focusstartproject.screens.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoanListFragmentTest : KTestCase() {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        navigateToAuthUserScreen()
    }

    @Test
    fun testLoanList() {
        run {
            step("Check loan list content") {
                checkLoans(
                    Loan(amount = "0", date = "06.12.2021"),
                    Loan(amount = "3000", date = "06.12.2021"),
                    Loan(amount = "5000", date = "04.12.2021"),
                    Loan(amount = "200", date = "04.12.2021")
                )
            }
        }
    }

    data class Loan(val amount: String, val date: String)

    private fun checkLoans(vararg loans: Loan) {
        loans.forEachIndexed { index, loan ->
            LoanListScreen {
                recyclerView {
                    childAt<LoanListScreen.LoanItem>(index) {
                        amount {
                            containsText(loan.amount)
                        }
                        date {
                            containsText(loan.date)
                        }
                    }
                }
            }
        }
    }

    @Test
    fun checkAddNewLoanButton() {
        run {
            step("Check add new loan button") {
                LoanListScreen {
                    addLoanButton {
                        click()
                    }
                }
            }
            step("Check that the open screen is add new loan screen") {
                NewLoanScreen {
                    conditions {
                        isDisplayed()
                    }
                    addLoanButton {
                        isClickable()
                    }
                }
            }
        }
    }

    @Test
    fun testNavigationToLoanDetails() {
        before {
            navigateToAuthUserScreen()
        }
            .after { }
            .run {
                step("Check that loan is clickable") {
                    LoanListScreen {
                        recyclerView.childAt<LoanListScreen.LoanItem>(0) {
                            click()
                        }
                    }
                }
                step("Check that the open screen is loan details screen") {
                    LoanDetailsScreen {
                        amount {
                            isDisplayed()
                        }
                        firstName {
                            isDisplayed()
                        }
                        lastName {
                            isDisplayed()
                        }
                        state {
                            isDisplayed()
                        }
                    }
                }
            }
    }

    private fun navigateToAuthUserScreen() {
        try {
            AuthorizationScreen {
                username {
                    replaceText("rmaya")
                }
                password {
                    replaceText("rmaya")
                }
                signInButton {
                    click()
                }
            }
            OnboardingScreen {
                skipButton {
                    click()
                }
            }
        } catch (exc: Exception) {
        }
    }
}