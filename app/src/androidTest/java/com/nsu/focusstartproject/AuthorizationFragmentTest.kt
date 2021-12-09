package com.nsu.focusstartproject

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nsu.focusstartproject.presentation.MainActivity
import com.nsu.focusstartproject.screens.AccountSettingsScreen
import com.nsu.focusstartproject.screens.AuthUserScreen
import com.nsu.focusstartproject.screens.AuthorizationScreen
import com.nsu.focusstartproject.screens.RegistrationScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthorizationFragmentTest : KTestCase() {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        exitToNoAuthUserScreen()
    }

    @Test
    fun testRegisterButton() {
        run {
            step("Click register button") {
                AuthorizationScreen {
                    registerButton {
                        click()
                    }
                }
            }
            step("Check that the open screen is registration screen") {
                RegistrationScreen {
                    username {
                        isClickable()
                    }
                    password {
                        isClickable()
                    }
                    signUpButton {
                        isClickable()
                    }
                }
            }
        }
    }


    @Test
    fun testSignInButton() {
        run {
            step("Exit to no auth user screen in necessary") {
                continuously(timeoutMs = 3000L) { exitToNoAuthUserScreen() }
            }
            step("Click sign in button") {
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
            }
            step("Check that the open screen is auth user screen") {
                AuthUserScreen {
                    tabLayout.perform {
                        isDisplayed()
                    }
                }
            }
        }
    }

    private fun exitToNoAuthUserScreen() {
        try {
            AuthUserScreen {
                tabLayout.perform {
                    onFailure { _, _ -> this.reset() }
                    selectTab(1)
                }
            }
            AccountSettingsScreen {
                signOutButton {
                    click()
                }
                alertDialog.positiveButton {
                    click()
                }
            }
        } catch (exc: Exception) {
        }

    }

}
