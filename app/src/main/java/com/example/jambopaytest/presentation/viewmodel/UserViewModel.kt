package com.example.jambopaytest.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jambopaytest.data.UserRepositoryImpl
import com.example.jambopaytest.data.model.User
import com.example.jambopaytest.data.remote.dto.toUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "UserViewModel"
@HiltViewModel
class UserViewModel @Inject constructor(private val userRepositoryImpl: UserRepositoryImpl): ViewModel() {

    private val _usersLiveData = MutableLiveData<List<User>>()

    private val _matchedUsersLiveData = MutableLiveData<List<User>>()
    val matchedUsersLiveData: LiveData<List<User>>
        get() = _matchedUsersLiveData

    private val _ageDataLiveData = MutableLiveData<Map<Int, Int>>()
    val ageDataLiveData: LiveData<Map<Int, Int>>
        get() = _ageDataLiveData

    private val matchedAges = mutableMapOf<Int, Int>() // Store matched age counts

    init {
        fetchUsers()
    }

    private fun fetchUsers(limit: Int = 0, skip: Int = 0) {
        viewModelScope.launch {
            try {
                val response = userRepositoryImpl.getAllUsers(limit, skip).users.map { it.toUser() }
                recordUserAges(response)
                Log.d(TAG, "fetchUsers: users are $response")
                _usersLiveData.value = response
            } catch (e: Exception) {
                e.printStackTrace() // Handle the error
            }
        }
    }

    // Function to generate a random age and check for matches
    fun checkRandomAge() {
        val randomAge = (18..60).random() // Assuming age range is between 18 and 60
        val users = _usersLiveData.value ?: return
        val matchedUsers = users.filter { it.age == randomAge }

        if (matchedUsers.isNotEmpty()) {
            _matchedUsersLiveData.value = matchedUsers
            matchedAges[randomAge] = matchedAges.getOrDefault(randomAge, 0) + matchedUsers.size
        } else {
            _matchedUsersLiveData.value = emptyList()
        }
    }

    // Function to record the age data for the graph
    private fun recordUserAges(users: List<User>) {
        val ageCountMap = mutableMapOf<Int, Int>()
        users.forEach { user ->
            val age = user.age
            Log.d(TAG, "recordUserAges: age is $age")
            ageCountMap[age] = (ageCountMap[age] ?: 0) + 1
        }
        _ageDataLiveData.value = ageCountMap
    }

}