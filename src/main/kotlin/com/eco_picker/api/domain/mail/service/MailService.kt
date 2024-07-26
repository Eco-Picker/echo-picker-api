package com.eco_picker.api.domain.mail.service


import com.eco_picker.api.domain.mail.constant.MailType
import com.eco_picker.api.global.data.properties.GeneralProperties
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import java.net.URLEncoder


@Component
class MailService(
    @Value("\${spring.mail.username}")
    private val from: String,
    private val generalProperties: GeneralProperties,
    private val mailSender: JavaMailSender,
    private val templateEngine: TemplateEngine
) {
    private val logger = KotlinLogging.logger { }

    fun sendVerify(username: String, email: String, token: String) {
        val verifyUrl = "${generalProperties.apiBaseUrl}/p/auth/verify_mail/${URLEncoder.encode(token, Charsets.UTF_8)}"
        val context = Context().apply {
            setVariable("username", username)
            setVariable("verifyUrl", verifyUrl)
        }
        val subject = "Complete sign up for Eco Picker"
        val template = "signup-verify.html"
        val body = templateEngine.process(template, context)

        send(MailType.SING_UP_VERIFY, email, subject, body)
    }

    fun sendTempPassword(username: String, email: String, password: String) {
        val context = Context().apply {
            setVariable("username", username)
            setVariable("tempPassword", password)
        }
        val subject = "Your Temporary Password"
        val template = "temp-password.html"
        val body = templateEngine.process(template, context)

        send(MailType.TEMP_PASSWORD, email, subject, body)
    }

    private fun send(type: MailType, to: String, subject: String, body: String) {
        val mimeMessage = mailSender.createMimeMessage()
        val mimeMessageHelper = MimeMessageHelper(mimeMessage, true, "UTF-8")
        mimeMessageHelper.setSubject(subject)
        mimeMessageHelper.setFrom(from, "Eco Picker")
        mimeMessageHelper.setText(body, true)
        mimeMessageHelper.setTo(to)
        try {
            logger.debug { "Attempting to send ${type.name} mail to $to" }
            mailSender.send(mimeMessage)
            logger.debug { "Successfully sent to $to" }
        } catch (e: Exception) {
            logger.error(e) { "Error occurred while send ${type.name} mail to $to" }
        }
    }
}