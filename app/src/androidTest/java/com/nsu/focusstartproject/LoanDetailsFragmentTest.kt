package com.nsu.focusstartproject

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nsu.focusstartproject.presentation.MainActivity
import com.nsu.focusstartproject.screens.AuthorizationScreen
import com.nsu.focusstartproject.screens.LoanDetailsScreen
import com.nsu.focusstartproject.screens.LoanListScreen
import com.nsu.focusstartproject.screens.OnboardingScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoanDetailsFragmentTest : KTestCase() {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        navigateToAuthUserScreen()
    }

    @Test
    fun testLoanDetailsContent() {
        run {
            step("Open loan details") {
                openLoanDetails(0)
            }
            step("Check loan details content") {
                LoanDetailsScreen {
                    amount {
                        hasText("0")
                    }
                    date {
                        containsText("06.12.2021")
                    }
                    firstName {
                        hasText("Илья")
                    }
                    lastName {
                        hasText("Ильин")
                    }
                    percent {
                        hasText("10.5%")
                    }
                }
            }
        }
    }

    @Test
    fun testNavigationOnBackToLoanList() {
        run {
            step("Open loan details") {
                openLoanDetails(0)
            }
            step("Check navigation back") {
                LoanDetailsScreen {
                    pressBack()
                }
                LoanListScreen {
                    recyclerView {
                        isDisplayed()
                    }
                }
            }
        }
    }

    private fun openLoanDetails(index: Int) {
        LoanListScreen {
            recyclerView.childAt<LoanListScreen.LoanItem>(index) {
                click()
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