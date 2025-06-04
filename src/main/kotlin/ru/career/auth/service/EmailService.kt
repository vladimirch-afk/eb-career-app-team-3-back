package ru.career.auth.service

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val mailSender: JavaMailSender
) {
    fun sendConfirmationCode(toEmail: String, code: String) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        helper.setTo(toEmail)
        helper.setSubject("Your confirmation code")
        helper.setText("Your confirmation code is: $code")

        mailSender.send(message)
    }
}
