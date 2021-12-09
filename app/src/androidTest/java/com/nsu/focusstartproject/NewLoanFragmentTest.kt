package com.nsu.focusstartproject

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nsu.focusstartproject.presentation.MainActivity
import com.nsu.focusstartproject.screens.AuthorizationScreen
import com.nsu.focusstartproject.screens.LoanListScreen
import com.nsu.focusstartproject.screens.NewLoanScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewLoanFragmentTest : KTestCase() {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testNewLoanScreenFields() {
        before {
            navigateToAuthUserScreen()
        }
            .after { }
            .run {
                step("Open new loan screen") {
                    openNewLoanScreen()
                }
                step("Check newLoan screen fields") {
                    NewLoanScreen {
                        conditions {
                            isDisplayed()
                        }
                        amount {
                            isClickable()
                        }
                        firstName {
                            isClickable()
                        }
                        lastName {
                            isClickable()
                        }
                        phoneNumber {
                            isClickable()
                        }
                        addLoanButton {
                            isClickable()
                        }
                    }
                }
            }
    }

    @Test
    fun testNavigationOnBackToLoanList() {
        before {
            navigateToAuthUserScreen()
        }
            .after { }
            .run {
                step("Open new loan screen") {
                    openNewLoanScreen()
                }
                step("Check navigation back") {
                    NewLoanScreen {
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

    private fun openNewLoanScreen() {
        LoanListScreen {
            addLoanButton {
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
        } catch (exc: Exception) {
        }
    }
}