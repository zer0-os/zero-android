package com.zero.android.network.service

import com.zero.android.network.model.ApiUser
import retrofit2.http.GET

interface UserService {

    @GET(value = "users/current")
    suspend fun getUser(): ApiUser
}
