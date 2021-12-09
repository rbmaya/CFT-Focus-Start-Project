package com.nsu.focusstartproject.screens

import com.nsu.focusstartproject.R
import io.github.kakaocup.kakao.dialog.KAlertDialog
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.switch.KSwitch
import io.github.kakaocup.kakao.text.KButton

object AccountSettingsScreen : Screen<AccountSettingsScreen>() {

    val signOutButton = KButton { withId(R.id.sign_out_button) }

    val alertDialog = KAlertDialog()

    val darkModeSwitch = KSwitch { withId(R.id.dark_mode_switch) }

}