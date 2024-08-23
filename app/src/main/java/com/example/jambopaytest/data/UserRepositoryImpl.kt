package com.example.jambopaytest.data

import com.example.jambopaytest.data.model.UsersResponse
import com.example.jambopaytest.data.remote.DummyApi
import com.example.jambopaytest.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val api: DummyApi): UserRepository {
    override suspend fun getAllUsers(limit: Int, skip: Int): UsersResponse {
        return api.getAllUsers(limit, skip)
    }

}