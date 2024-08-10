package com.eco_picker.api.global.support

import com.eco_picker.api.domain.newsletter.constant.NewsletterCategory
import com.eco_picker.api.domain.newsletter.data.Newsletter
import org.springframework.stereotype.Component
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Value
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class GeminiResponse(val name: String, val category: String)

private val logger = KotlinLogging.logger {}

@Component
class GeminiManager {

    @Value("\${gemini.app-key}")
    private lateinit var apiKey: String

    private val client = OkHttpClient()
    private val mapper = jacksonObjectMapper()

    fun analyzeImage(base64Image: String): GeminiResponse? {
        val requestJson = """
            {
              "contents": [
                {
                  "parts": [
                    {"text": "This photo was taken by a user of our service who picked up this garbage. Please analyze the photo and provide two pieces of information: 1) Identify the name of the garbage. 2) Choose the garbage category from these 6 options: PLASTIC, METAL, GLASS, CARDBOARD_PAPER, FOOD_SCRAPS, OTHER."},
                    {
                      "inline_data": {
                        "mime_type": "image/jpeg",
                        "data": "$base64Image"
                      }
                    }
                  ]
                }
              ]
            }
        """.trimIndent()

        logger.debug { "Request JSON: $requestJson" }

        val requestBody: RequestBody = requestJson.toRequestBody("application/json".toMediaTypeOrNull())
        val request = Request.Builder()
            .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=$apiKey")
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            val responseBody = response.body?.string()
            logger.debug { "Response code: ${response.code}" }
            logger.debug { "Response body: $responseBody" }

            if (!response.isSuccessful) throw RuntimeException("Unexpected code $response")

            val responseJson = mapper.readTree(responseBody)
            val textParts = responseJson["candidates"]?.get(0)?.get("content")?.get("parts")?.get(0)?.get("text")?.asText()?.split("\n")

            if (textParts != null && textParts.size >= 2) {
                val name = textParts[0].substringAfter(") ")
                val category = textParts[1].substringAfter(") ")
                return GeminiResponse(name, category)
            }
        }
        return null
    }

    fun generateNewsletter(category: NewsletterCategory, n: Int): List<Newsletter> {
        val promptText = when (category) {
            NewsletterCategory.NEWS -> """
            Provide the latest news related to recycling and the environment, focusing on recent developments, policy changes, scientific advancements, and environmental impacts.
            
            **Important Requirements:**
            - Only return real and verifiable news with valid URLs that are currently accessible.
            - Do not generate or fabricate news or sources.
            - Ensure that the URLs provided link to live pages with actual news details.
            - If a verifiable source cannot be found, return a null value for that entry instead.
            - Format the `publishedAt` field in the JSON output as "yyyy-MM-dd HH:mm:ss". If the time is not available, use "00:00:00".
            
            The following information should be in JSON format:
            
            {"title": "must be 40 characters or less", "content": "must be 500 characters or less", "category": "NEWS", "publishedAt": "format: yyyy-MM-dd HH:mm:ss", "source": "must be a valid, live link; do not generate or fabricate sources"}
            
            Provide $n entries based on the latest available information as of today.
            """.trimIndent()


            NewsletterCategory.EDUCATION -> """
            Provide educational content related to recycling and the environment, such as guides, tutorials, informative articles, and best practices. Examples may include recycling tips, environmental impact studies, and sustainability practices.
            
            **Important Requirements:**
            - Only return real and verifiable content with valid URLs that are currently accessible.
            - Do not generate or fabricate content or sources.
            - Ensure that the URLs provided link to live pages with actual educational content.
            - If a verifiable source cannot be found, return a null value for that entry instead.
            - Format the `publishedAt` field in the JSON output as "yyyy-MM-dd HH:mm:ss". If the time is not available, use "00:00:00".
            
            The following information should be in JSON format:
            
            {"title": "must be 40 characters or less", "content": "must be 500 characters or less", "category": "EDUCATION", "publishedAt": "format: yyyy-MM-dd HH:mm:ss", "source": "must be a valid, live link; do not generate or fabricate sources"}
            
            Provide $n entries based on the latest available information as of today.
            """.trimIndent()


            NewsletterCategory.EVENT -> """
        Provide information on upcoming online or offline events in the USA or Canada related to recycling, environmental protection, or up-cycling that individuals, families, or friends can participate in. These should include community cleanup events, recycling workshops, webinars, and environmental fairs.

        **Important Requirements:**
        - Only return real and verifiable events with valid URLs that are currently accessible.
        - Do not generate or fabricate events or sources.
        - Ensure that the URLs provided link to live pages with actual event details.
        - If a verifiable event cannot be found, return a null value for that entry instead.
        - The following information should be in JSON format:

        {"title": "must be 40 characters or less", "content": "must be 500 characters or less", "category": "EVENT", "publishedAt": "format: yyyy-MM-dd HH:mm:ss (time is optional)", "source": "must be a valid, live link; do not generate or fabricate sources"}

        Provide $n entries based on the latest available information as of today.
    """.trimIndent()
        }

        logger.debug { "Prompt text: $promptText" }

        val requestJson = """
    {
      "contents": [
        {
          "parts": [
            {
              "text": ${mapper.writeValueAsString(promptText)}
            }
          ]
        }
      ]
    }
    """.trimIndent()

        logger.debug { "Request JSON: $requestJson" }

        val requestBody = requestJson.toRequestBody("application/json".toMediaTypeOrNull())
        val request = Request.Builder()
            .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=$apiKey")
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            val responseBody = response.body?.string()

            logger.debug { "Response code: ${response.code}" }
            logger.debug { "Response body: $responseBody" }

            if (!response.isSuccessful) {
                logger.error { "Request failed with code: ${response.code}" }
                throw RuntimeException("Unexpected code $response")
            }

            val responseJson = mapper.readTree(responseBody)
            logger.debug { "Response JSON: $responseJson" }

            val newsletterList = mutableListOf<Newsletter>()

            responseJson["candidates"]?.forEach { candidate ->
                val parts = candidate["content"]?.get("parts")
                parts?.forEach { part ->
                    val text = part["text"]?.asText()?.trim()
                    val jsonData = text?.substringAfter("```json")?.substringBefore("```")

                    if (jsonData != null && jsonData.isNotBlank()) {
                        try {
                            val newsletters: List<Map<String, String>> = mapper.readValue(jsonData)
                            newsletters.forEach { entry ->
                                val title = entry["title"]
                                val content = entry["content"]
                                val categoryStr = entry["category"]
                                val publishedAtStr = entry["publishedAt"]
                                val source = entry["source"]

                                val publishedAt = try {
                                    publishedAtStr?.let {
                                        LocalDateTime.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                    }
                                } catch (e: Exception) {
                                    logger.error { "Failed to parse date: $publishedAtStr" }
                                    null
                                }

                                if (title != null && content != null && categoryStr != null && source != null) {
                                    val newsletter = publishedAt?.atZone(ZoneId.systemDefault())?.let {
                                        Newsletter(
                                            title = title,
                                            content = content,
                                            category = NewsletterCategory.valueOf(categoryStr),
                                            source = source,
                                            publishedAt = it
                                        )
                                    }
                                    logger.debug { "Successfully added newsletter: $title" }
                                    if (newsletter != null) {
                                        newsletterList.add(newsletter)
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            logger.error { "Failed to parse JSON: $jsonData" }
                        }
                    } else {
                        logger.debug { "No valid JSON data found." }
                    }
                }
            }
            return newsletterList
        }
    }
}