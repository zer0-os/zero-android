package com.zero.android.data.manager

interface ConnectionManager {

    suspend fun connect()

    suspend fun disconnect()
}
