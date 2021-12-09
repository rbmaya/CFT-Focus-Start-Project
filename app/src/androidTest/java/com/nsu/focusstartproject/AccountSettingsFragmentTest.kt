package com.nsu.focusstartproject

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nsu.focusstartproject.presentation.MainActivity
import com.nsu.focusstartproject.screens.AccountSettingsScreen
import com.nsu.focusstartproject.screens.AuthUserScreen
import com.nsu.focusstartproject.screens.AuthorizationScreen
import com.nsu.focusstartproject.screens.OnboardingScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AccountSettingsFragmentTest : KTestCase() {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        navigateToAuthUserScreen()
    }

    @Test
    fun testScreenContent() {
        run {
            step("Navigate to account settings screen") {
                AuthUserScreen {
                    tabLayout {
                        selectTab(1)
                    }
                }
            }
            step("Check screen content") {
                AccountSettingsScreen {
                    darkModeSwitch {
                        isClickable()
                    }
                    signOutButton {
                        isClickable()
                    }
                }
            }
        }
    }

    @Test
    fun testSignOutButton() {
        run {
            step("Navigate to account settings screen") {
                AuthUserScreen {
                    tabLayout.selectTab(1)
                }
            }
            step("Check sign out button") {
                AccountSettingsScreen {
                    signOutButton {
                        click()
                    }
                    alertDialog.positiveButton {
                        click()
                    }
                }
                AuthorizationScreen {
                    signInButton {
                        isClickable()
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