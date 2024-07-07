package com.santoshdevadiga.sampleapp.repository.remote

import com.santoshdevadiga.sampleapp.models.UserPostList
import retrofit2.Response
import retrofit2.http.GET

interface AppServiceAPI {
    @GET("/posts")
    suspend fun getUserPostList():Response<UserPostList>
}