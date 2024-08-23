package com.example.jambopaytest.domain.repository

import com.example.jambopaytest.data.model.UsersResponse

interface UserRepository {

    suspend fun getAllUsers(limit: Int, skip: Int): UsersResponse

}