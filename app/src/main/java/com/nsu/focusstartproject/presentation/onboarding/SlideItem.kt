package com.nsu.focusstartproject.presentation.onboarding

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class SlideItem(
    @DrawableRes
    val drawableRes: Int,
    val text: String
) : Parcelable