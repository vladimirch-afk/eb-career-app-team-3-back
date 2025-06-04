package ru.career.auth.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.career.auth.repo.UserRepository
import ru.career.auth.repo.dto.User

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun registerUser(firstName: String, lastName: String, username: String, rawPassword: String): User {
        if (userRepository.findByUsername(username) != null) {
            throw IllegalArgumentException("User already exists")
        }

        val encodedPassword = passwordEncoder.encode(rawPassword)
        val user = User(
            firstName = firstName,
            lastName = lastName,
            username = username,
            password = encodedPassword
        )

        return userRepository.save(user)
    }

    fun authenticate(username: String, password: String): Boolean {
        val user = userRepository.findByUsername(username) ?: return false
        return passwordEncoder.matches(password, user.password)
    }
}
