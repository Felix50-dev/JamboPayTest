package com.example.jambopaytest.data.remote

import com.example.jambopaytest.data.model.UsersResponse
import com.example.jambopaytest.data.remote.dto.UserDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DummyApi {

    @GET("/users")
    suspend fun getAllUsers(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): UsersResponse

    companion object {
        fun create(): DummyApi {
            return Retrofit.Builder()
                .baseUrl("https://dummyjson.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DummyApi::class.java)
        }
    }

}