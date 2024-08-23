package com.example.jambopaytest.data.model

import com.example.jambopaytest.data.remote.dto.UserDto

data class UsersResponse(
    val users: List<UserDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)