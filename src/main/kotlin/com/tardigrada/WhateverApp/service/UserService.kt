package com.tardigrada.WhateverApp.service

import com.tardigrada.WhateverApp.inputValidator.InputValidator
import com.tardigrada.WhateverApp.model.User
import com.tardigrada.WhateverApp.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService (private val userRepository: UserRepository, private val inputValidator: InputValidator) {

    fun saveUser(user: User): User {
        if (!(inputValidator.inputCheck(user.firstName, user.lastName, user.email, user.dateOfBirth, user.street,
                user.city, user.postcode, user.telephoneNumber)) || !(inputValidator.emailCheck(user.email))) {
            throw java.lang.IllegalArgumentException("Fill all the fields in correct format.")
        }
        if(getUsers().any { it.email == user.email }) {
            throw java.lang.IllegalArgumentException("User with e-mail ${user.email} already exists.")
        }
        return userRepository.save(user)
    }

    fun getUsers(): MutableIterable<User> {return userRepository.findAll() }

    fun getUserById(userId: Int): User {
        if (userRepository.findById(userId).isEmpty) {
            throw NoSuchElementException("Could not find user with id $userId.")
        }
        return userRepository.findById(userId).get()
    }

    fun updateUserById(user: User, userId: Int): User {
        if (userRepository.findById(userId).isEmpty) {
            throw NoSuchElementException("Could not find user with id $userId.")
        }
        deleteUserById(userId)
        return saveUser(user)
    }

    fun deleteUserById(userId: Int): Unit {
        if (userRepository.findById(userId).isEmpty) {
            throw NoSuchElementException("Could not find user with id $userId.")
        }
        return userRepository.deleteById(userId)
    }

    fun deleteUserByEmail(email: String): Unit {
        if (userRepository.findByEmail(email).isEmpty) {
            throw NoSuchElementException("Could not find user with e-mail $email.")
        }
        return userRepository.deleteByEmail(email)
    }
}