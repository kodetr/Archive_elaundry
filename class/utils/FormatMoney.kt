package com.kodetr.elaundry.utils

import java.text.NumberFormat
import java.util.Locale

object FormatMoney {
    fun formatMoney(): NumberFormat {
        val locale = Locale("in", "ID")
        return NumberFormat.getCurrencyInstance(locale)
    }
}
