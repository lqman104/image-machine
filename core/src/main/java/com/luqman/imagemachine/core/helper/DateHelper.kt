package com.luqman.imagemachine.core.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateHelper {

    private const val SIMPLE_DATE = "dd-MM-yyyy"

    fun Long?.toDate(toFormat: String = SIMPLE_DATE): String {
        return if (this == null || this == 0L) {
            ""
        } else {
            val simpleDateFormat = SimpleDateFormat(toFormat, Locale.getDefault())
            val date = Date(this)
            simpleDateFormat.format(date)
        }
    }

    fun String?.dateToLong(toFormat: String = SIMPLE_DATE): Long {
        return if (this.isNullOrEmpty()) {
            0
        } else {
            val simpleDateFormat = SimpleDateFormat(SIMPLE_DATE, Locale.getDefault())
            val date = simpleDateFormat.parse(this)
            date?.time ?: 0
        }
    }
}