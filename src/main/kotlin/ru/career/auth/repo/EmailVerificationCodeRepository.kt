package ru.career.auth.repo

import org.springframework.data.jpa.repository.JpaRepository
import ru.career.auth.repo.dto.EmailVerificationCode

interface EmailVerificationCodeRepository : JpaRepository<EmailVerificationCode, Long> {
    fun findByEmail(email: String): EmailVerificationCode?
}
