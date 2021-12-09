package com.nsu.focusstartproject.screens

import com.nsu.focusstartproject.R
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.tabs.KTabLayout

object AuthUserScreen : Screen<AuthUserScreen>() {

    val tabLayout = KTabLayout { withId(R.id.tab_layout) }

}