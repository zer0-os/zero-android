package com.zero.android.common.util

object CurrencyUtil {

    fun fromCents(cents: Double?) = cents?.let { it / 100.0 } ?: 0.0
}
