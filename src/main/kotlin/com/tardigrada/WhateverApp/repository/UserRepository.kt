package com.tardigrada.WhateverApp.repository

import com.tardigrada.WhateverApp.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : CrudRepository<User, Int> {

    fun findByEmail(email: String): Optional<User>
    fun deleteByEmail(email: String)

}