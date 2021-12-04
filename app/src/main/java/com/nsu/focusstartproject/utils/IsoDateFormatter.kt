package com.nsu.focusstartproject.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object IsoDateFormatter {

    @RequiresApi(Build.VERSION_CODES.O)
    fun format(date: String): String {
        try {
            val parsedDate = LocalDateTime.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            return parsedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        } catch (exc: Exception) {
            exc.printStackTrace()
        }
        return ""
    }

}