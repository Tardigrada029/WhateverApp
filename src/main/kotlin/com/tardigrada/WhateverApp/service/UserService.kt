package com.tardigrada.WhateverApp.service

import com.tardigrada.WhateverApp.model.User
import com.tardigrada.WhateverApp.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun saveUser(user: User) = userRepository.save(user)

    fun getUsers() = userRepository.findAll()

    fun getUserByUuid(userUuid: Int) = userRepository.findById(userUuid)

    fun updateUserByUuid(user: User, userUuid: Int) {
        deleteUserByUuid(userUuid)
        saveUser(user)
    }

    fun deleteUserByUuid(userUuid: Int) = userRepository.deleteById(userUuid)

}