package com.tardigrada.WhateverApp.service

import com.tardigrada.WhateverApp.inputValidator.InputValidator
import com.tardigrada.WhateverApp.model.User
import com.tardigrada.WhateverApp.repository.UserRepository
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.testng.Assert.*
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import java.time.LocalDate
import java.util.*
import kotlin.NoSuchElementException

@SpringBootTest
class UserServiceTest {

    lateinit var mockUserRepository: UserRepository
    lateinit var mockInputValidator: InputValidator
    lateinit var userService: UserService

    private final val USER_ID = 0
    private final val FIRST_NAME = "Kate"
    private final val LAST_NAME = "Green"
    private final val EMAIL = "green@gmail.com"
    private final val DATE_OF_BIRTH = LocalDate.of(1986, 4, 8)
    private final val STREET = "Long"
    private final val CITY = "London"
    private final val POSTCODE = "E1 6AN"
    private final val TELEPHONE_NUMBER = "+447911123456"
    private final val USER = User(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, DATE_OF_BIRTH, STREET, CITY, POSTCODE,
        TELEPHONE_NUMBER)

    @BeforeMethod
    fun setupBeforeClass() {
        mockUserRepository = Mockito.mock(UserRepository::class.java)
        mockInputValidator = Mockito.mock(InputValidator::class.java)
        userService = UserService(mockUserRepository, mockInputValidator)
    }

    // ***************************** saveUser() ************************************************
    @Test
    fun `should create new user`() {
        // given
        Mockito.`when`(
            mockInputValidator.inputCheck(
                FIRST_NAME, LAST_NAME, EMAIL, DATE_OF_BIRTH, STREET, CITY, POSTCODE,
                TELEPHONE_NUMBER
            )
        ).thenReturn(true)
        Mockito.`when`(mockInputValidator.emailCheck(EMAIL)).thenReturn(true)
        Mockito.`when`(mockUserRepository.save(USER)).thenReturn(USER)

        // when
        val savedUser = userService.saveUser(USER)

        // then
        assertEquals(savedUser, USER)
    }

    @Test
    fun `should throw IllegalArgumentException when there is an incorrect email format while creating new user`() {
        // given
        Mockito.`when`(
            mockInputValidator.inputCheck(
                FIRST_NAME, LAST_NAME, EMAIL, DATE_OF_BIRTH, STREET, CITY, POSTCODE,
                TELEPHONE_NUMBER
            )
        ).thenReturn(true)
        Mockito.`when`(mockInputValidator.emailCheck(EMAIL)).thenReturn(false)
        Mockito.`when`(mockUserRepository.save(USER)).thenReturn(USER)

        // when & then
        expectThrows(IllegalArgumentException::class.java) { userService.saveUser(USER) }
    }

    @Test
    fun `should throw IllegalArgumentException when there is blank variable while creating new user`() {
        // given
        Mockito.`when`(
            mockInputValidator.inputCheck(
                FIRST_NAME, LAST_NAME, EMAIL, DATE_OF_BIRTH, STREET, CITY, POSTCODE,
                TELEPHONE_NUMBER
            )
        ).thenReturn(false)
        Mockito.`when`(mockInputValidator.emailCheck(EMAIL)).thenReturn(true)
        Mockito.`when`(mockUserRepository.save(USER)).thenReturn(USER)

        // when & then
        expectThrows(IllegalArgumentException::class.java) { userService.saveUser(USER) }
    }

    @Test
    // @Ignore
    fun `should throw IllegalArgumentException while creating new user with existing email`() {
        // given
        Mockito.`when`(
            mockInputValidator.inputCheck(
                FIRST_NAME, LAST_NAME, EMAIL, DATE_OF_BIRTH, STREET, CITY, POSTCODE,
                TELEPHONE_NUMBER
            )
        ).thenReturn(true)
        Mockito.`when`(mockInputValidator.emailCheck(EMAIL)).thenReturn(true)
        Mockito.`when`(mockUserRepository.findAll()).thenReturn(mutableListOf(USER))
        Mockito.`when`(mockUserRepository.save(USER)).thenReturn(USER)

        // when & then
        expectThrows(IllegalArgumentException::class.java) { userService.saveUser(USER) }
    }

    // ***************************** getUsers() ************************************************
    @Test
    fun `should return all existing saved users`() {
        // given
        Mockito.`when`(mockUserRepository.findAll()).thenReturn(mutableListOf(USER))

        // when
        val numberOfUsers = userService.getUsers().count()

        // then
        assertEquals(numberOfUsers, 1)
    }

    // ***************************** getUserById() ************************************************
    @Test
    fun `should return existing saved user with given id`() {
        // given
        Mockito.`when`(mockUserRepository.findById(0)).thenReturn(Optional.of(USER))

        // when
        val result = userService.getUserById(0)

        // then
        assertEquals(USER, result)
    }

    @Test
    fun `should throw NoSuchElementException when there is no saved user`() {
        // given
        Mockito.`when`(mockUserRepository.findById(0)).thenReturn(Optional.empty())

        // when & then
        expectThrows(NoSuchElementException::class.java) { userService.getUserById(0) }
    }

    // ***************************** updateUserById() ************************************************
    @Test
    fun `should update existing user with given id`() {
        // given
        Mockito.`when`(mockUserRepository.findById(0)).thenReturn(Optional.of(USER))
        Mockito.`when`(
            mockInputValidator.inputCheck(
                FIRST_NAME, LAST_NAME, EMAIL, DATE_OF_BIRTH, STREET, CITY, POSTCODE,
                TELEPHONE_NUMBER
            )
        ).thenReturn(true)
        Mockito.`when`(mockInputValidator.emailCheck(EMAIL)).thenReturn(true)
        Mockito.`when`(mockUserRepository.save(USER)).thenReturn(USER)
        Mockito.doNothing().`when`(mockUserRepository).deleteById(USER_ID)

        // when
        val result = userService.updateUserById(USER, USER_ID)

        // then
        assertEquals(result, USER)
    }

    @Test
    fun `should throw NoSuchElementException when user with given id is not present to update`() {
        // given
        Mockito.`when`(mockUserRepository.findById(0)).thenReturn(Optional.empty())
        Mockito.`when`(
            mockInputValidator.inputCheck(
                FIRST_NAME, LAST_NAME, EMAIL, DATE_OF_BIRTH, STREET, CITY, POSTCODE,
                TELEPHONE_NUMBER
            )
        ).thenReturn(true)
        Mockito.`when`(mockInputValidator.emailCheck(EMAIL)).thenReturn(true)
        Mockito.`when`(mockUserRepository.save(USER)).thenReturn(USER)
        Mockito.doNothing().`when`(mockUserRepository).deleteById(USER_ID)

        // when & then
        expectThrows(NoSuchElementException::class.java) { userService.updateUserById(USER, USER_ID) }
    }

    // ***************************** deleteUserById() ************************************************
    @Test
    fun `should delete existing user with given id`() {
        // given
        Mockito.`when`(mockUserRepository.findById(USER_ID)).thenReturn(Optional.of(USER))
        Mockito.doNothing().`when`(mockUserRepository).deleteById(USER_ID)

        // when
        val result = userService.deleteUserById(USER_ID)

        // then
        assertEquals(result, Unit)
    }

    @Test
    fun `should throw NoSuchElementException when user with given id is not present to delete`() {
        // given
        Mockito.`when`(mockUserRepository.findById(USER_ID)).thenReturn(Optional.empty())
        Mockito.doNothing().`when`(mockUserRepository).deleteById(USER_ID)

        // when & then
        expectThrows(NoSuchElementException::class.java) { userService.deleteUserById(USER_ID) }
    }
}
