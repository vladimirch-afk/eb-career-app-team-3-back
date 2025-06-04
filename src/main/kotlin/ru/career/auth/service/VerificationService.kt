package ru.career.auth.service

import org.springframework.stereotype.Service
import ru.career.auth.repo.EmailVerificationCodeRepository
import ru.career.auth.repo.dto.EmailVerificationCode
import java.time.LocalDateTime
import kotlin.random.Random

@Service
class VerificationService(
    private val emailService: EmailService,
    private val codeRepo: EmailVerificationCodeRepository
) {

    fun sendCode(email: String) {
        val code = generateCode()
        val expiresAt = LocalDateTime.now().plusMinutes(10)

        val existing = codeRepo.findByEmail(email)
        if (existing != null) {
            codeRepo.delete(existing)
        }

        val entity = EmailVerificationCode(
            email = email,
            code = code,
            expiresAt = expiresAt
        )
        codeRepo.save(entity)

        emailService.sendConfirmationCode(email, code)
    }

    private fun generateCode(): String {
        return Random.nextInt(100000, 999999).toString()
    }

    fun verifyCode(email: String, code: String) {
        val record = codeRepo.findByEmail(email)
            ?: throw IllegalArgumentException("Verification code not found")

        if (record.expiresAt.isBefore(LocalDateTime.now())) {
            codeRepo.delete(record)
            throw IllegalArgumentException("Verification code expired")
        }

        if (record.code != code) {
            throw IllegalArgumentException("Invalid verification code")
        }

        codeRepo.delete(record)
    }

}
