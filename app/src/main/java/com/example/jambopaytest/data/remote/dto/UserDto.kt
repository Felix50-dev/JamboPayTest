package com.example.jambopaytest.data.remote.dto

import com.example.jambopaytest.data.model.User

data class UserDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val maidenName: String,
    val age: Int,
    val gender: String,
    val email: String,
    val phone: String,
    val username: String,
    val password: String,
    val birthDate: String,
    val image: String,
    val bloodGroup: String,
    val height: Double,
    val weight: Double,
    val eyeColor: String,
    val hair: Hair,
    val ip: String,
    val address: Address,
    val macAddress: String,
    val university: String,
    val bank: Bank,
    val company: Company,
    val ein: String,
    val ssn: String,
    val userAgent: String,
    val crypto: Crypto,
    val role: String
)

fun UserDto.toUser(): User {
    return User(
        id = id,
        firstName = firstName,
        age = age,
        email = email,
        image = image
    )
}