package ru.career.auth.repo.dto

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "email_verification_codes")
data class EmailVerificationCode(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    val code: String,

    @Column(nullable = false)
    val expiresAt: LocalDateTime
)
