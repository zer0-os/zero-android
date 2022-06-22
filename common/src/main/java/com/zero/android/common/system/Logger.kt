package com.zero.android.common.system

interface Logger {

    fun setup(debug: Boolean)

    fun v(message: String)
    fun v(t: Throwable, message: String? = null)

    fun d(message: String)
    fun d(t: Throwable, message: String? = null)

    fun i(message: String)
    fun i(t: Throwable, message: String? = null)

    fun w(message: String)
    fun w(t: Throwable, message: String? = null)

    fun e(message: String, t: Throwable? = null)
    fun e(t: Throwable)
}
