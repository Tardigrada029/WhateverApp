package com.tardigrada.WhateverApp.repository

import com.tardigrada.WhateverApp.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Int>