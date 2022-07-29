package com.tardigrada.WhateverApp.controller

import com.tardigrada.WhateverApp.model.User
import com.tardigrada.WhateverApp.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleNotFound(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping
    fun getUsers() = userService.getUsers()

    @GetMapping("/{userUuid}")
    fun getUserByUuid(@PathVariable userUuid: Int) = userService.getUserByUuid(userUuid)

    @PostMapping
    fun saveUser(@RequestBody user: User) = userService.saveUser(user)

    @PatchMapping("/{userUuid}")
    fun updateUserByUuid(@PathVariable userUuid: Int, @RequestBody user: User) = userService.updateUserByUuid(user, userUuid)

    @DeleteMapping("/{userUuid}")
    fun deleteUserByUuid(@PathVariable userUuid: Int) = userService.deleteUserByUuid(userUuid)

}