package com.tardigrada.WhateverApp.service

import com.tardigrada.WhateverApp.model.User
import com.tardigrada.WhateverApp.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun saveUser(user: User): User {
        if(getUsers().any { it.email == user.email }) {
            throw java.lang.IllegalArgumentException("User with e-mail ${user.email} already exists.")
        }
        return userRepository.save(user)
    }

    fun getUsers(): MutableIterable<User> {return userRepository.findAll() }

    fun getUserByUuid(userUuid: Int): User {
        if (userRepository.findById(userUuid).isEmpty) {
            throw NoSuchElementException("Could not find user with id $userUuid")
        }
        return userRepository.findById(userUuid).get()
    }

    fun updateUserByUuid(user: User, userUuid: Int): User {
        if (userRepository.findById(userUuid).isEmpty) {
            throw NoSuchElementException("Could not find user with id $userUuid")
        }
        deleteUserByUuid(userUuid)
        return saveUser(user)
    }

    fun deleteUserByUuid(userUuid: Int): Unit {
        if (userRepository.findById(userUuid).isEmpty) {
            throw NoSuchElementException("Could not find user with id $userUuid")
        }
        return userRepository.deleteById(userUuid)
    }

}