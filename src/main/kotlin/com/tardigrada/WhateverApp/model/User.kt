package com.tardigrada.WhateverApp.model

import javax.persistence.*

@Entity
@Table(name = "tbl_users")
data class User(

    // TODO: UUID will be created
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_uuid")
    val userUuid: Int,

    @Column(nullable = false, length = 50, name = "first_name")
    val firstName: String,

    @Column(nullable = false, length = 50, name = "last_name")
    val lastName: String,

    @Column(unique = true, nullable = false, length = 100, name = "email")
    val email: String,

    // TODO: age will be calculated from date of birth
    @Column(nullable = false, name = "age")
    val age: Int,

    @Column(nullable = false, length = 50, name = "street")
    val street: String,

    @Column(nullable = false, length = 50, name = "city")
    val city: String,

    @Column(nullable = false, length = 50, name = "postcode")
    val postcode: String,

    @Column(nullable = false, length = 50, name = "telephone_number")
    val telephoneNumber: String

)
