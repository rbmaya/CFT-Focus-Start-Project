package com.nsu.focusstartproject.utils.view_actions

import android.animation.ObjectAnimator
import android.view.View

object Animations {
    private const val AMPLITUDE = 10F

    fun View.wiggle(duration: Long) {
        ObjectAnimator.ofFloat(this, View.TRANSLATION_X, 0F, -AMPLITUDE, AMPLITUDE, 0F).apply {
            repeatCount = 1
            this.duration = duration
            start()
        }
    }
}