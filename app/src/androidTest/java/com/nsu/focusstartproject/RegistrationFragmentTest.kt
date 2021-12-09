package com.nsu.focusstartproject

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nsu.focusstartproject.presentation.MainActivity
import com.nsu.focusstartproject.screens.AccountSettingsScreen
import com.nsu.focusstartproject.screens.AuthUserScreen
import com.nsu.focusstartproject.screens.AuthorizationScreen
import com.nsu.focusstartproject.screens.RegistrationScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistrationFragmentTest : KTestCase() {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testRegisterScreen() {
        before {
            exitToNoAuthUserScreen()
        }
            .after { }
            .run {
                step("Navigate to register screen") {
                    AuthorizationScreen {
                        registerButton {
                            click()
                        }
                    }
                }
                step("Check registration screen") {
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