package ru.career.auth.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.career.auth.service.UserService
import ru.career.auth.service.VerificationService

@RestController
@RequestMapping("/auth")
class AuthController(
    private val userService: UserService,
    private val verificationService: VerificationService
) {

    data class RegisterRequest(
        val firstName: String,
        val lastName: String,
        val username: String,
        val password: String
    )

    data class LoginRequest(
        val username: String,
        val password: String
    )

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<String> {
        return try {
            userService.registerUser(
                firstName = request.firstName,
                lastName = request.lastName,
                username = request.username,
                rawPassword = request.password
            )
            ResponseEntity.ok("User registered successfully")
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<String> {
        return if (userService.authenticate(request.username, request.password)) {
            ResponseEntity.ok("Login successful")
        } else {
            ResponseEntity.status(401).body("Invalid credentials")
        }
    }

    @PostMapping("/send-code")
    fun sendCode(@RequestBody request: SendCodeRequest): ResponseEntity<String> {
        return try {
            verificationService.sendCode(request.email)
            ResponseEntity.ok("Verification code sent")
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body("Failed to send verification code")
        }
    }

    @PostMapping("/verify")
    fun verifyEmail(@RequestBody request: VerifyCodeRequest): ResponseEntity<String> {
        return try {
            verificationService.verifyCode(request.email, request.code)
            ResponseEntity.ok("Email verified successfully")
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    data class VerifyCodeRequest(val email: String, val code: String)


    data class SendCodeRequest(val email: String)
}
