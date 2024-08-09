package com.eco_picker.api.global.support

import com.eco_picker.api.domain.newsletter.constant.NewsletterCategory
import com.eco_picker.api.domain.newsletter.data.Newsletter
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Value
import java.net.HttpURLConnection
import java.net.URL
import java.util.Base64
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.format.DateTimeFormatter

data class GeminiResponse(val name: String, val category: String)

private val logger = KotlinLogging.logger {}

@Component
class GeminiManager {

    @Value("\${gemini.app-key}")
    private lateinit var apiKey: String

    private val client = OkHttpClient()
    private val mapper = jacksonObjectMapper()

//    fun analyzeImage(base64Image: String): GeminiResponse? {
//        val requestJson = """
//            {
//              "contents": [
//                {
//                  "parts": [
//                    {"text": "This photo was taken by a user of our service who picked up this garbage. Please analyze the photo and provide two pieces of information: 1) Identify the name of the garbage. 2) Choose the garbage category from these 6 options: PLASTIC, METAL, GLASS, CARDBOARD_PAPER, FOOD_SCRAPS, OTHER."},
//                    {
//                      "inline_data": {
//                        "mime_type": "image/jpeg",
//                        "data": "$base64Image"
//                      }
//                    }
//                  ]
//                }
//              ]
//            }
//        """.trimIndent()
//
//        val requestBody: RequestBody = requestJson.toRequestBody("application/json".toMediaTypeOrNull())
//        val request = Request.Builder()
//            .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=$apiKey")
//            .post(requestBody)
//            .build()
//
//        client.newCall(request).execute().use { response ->
//            if (!response.isSuccessful) throw RuntimeException("Unexpected code $response")
//
//            val responseBody = response.body?.string()
//            val responseJson = mapper.readTree(responseBody)
//            val textParts = responseJson["candidates"]?.get(0)?.get("content")?.get("parts")?.get(0)?.get("text")?.asText()?.split("\n")
//
//            if (textParts != null && textParts.size >= 2) {
//                val name = textParts[0].substringAfter(") ")
//                val category = textParts[1].substringAfter(") ")
//                return GeminiResponse(name, category)
//            }
//        }
//        return null
//    }
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
        Provide the latest news related to recycling and the environment, focusing on recent developments, policy changes, scientific advancements, and environmental impacts. Format the information as specified below, and provide a total of $n entries.

        **IMPORTANT:** Adhere to the character limits strictly.
        - title: (must be 40 characters or less)
        - content: (must be 500 characters or less)
        - category: "NEWS"
        - publishedAt: The date of creation from the source (e.g., "2023-10-27 15:30:40")
        - source: URL of the source

        Ensure all information is the latest available as of today and is based on Canada or USA. Provide only information with clear sources; do not generate or fabricate sources. All fields are mandatory.
    """.trimIndent()

            NewsletterCategory.EDUCATION -> """
        Provide educational content related to recycling and the environment, including guides, tutorials, informative articles, and best practices. Examples may include recycling tips, environmental impact studies, and sustainability practices. Format the information as specified below, and provide a total of $n entries.

        **IMPORTANT:** Adhere to the character limits strictly.
        - title: (must be 40 characters or less)
        - content: (must be 500 characters or less)
        - category: "EDUCATION"
        - publishedAt: The date of creation from the source (e.g., "2023-10-27 15:30:40")
        - source: URL of the source

        Ensure all information is the latest available as of today and is based on Canada or USA. Provide only information with clear sources; do not generate or fabricate sources. All fields are mandatory.
    """.trimIndent()

            NewsletterCategory.EVENT -> """
        Provide information on online or offline events happening in the future that individuals, families, or friends can participate in, related to recycling and the environment. Examples include community cleanup events, recycling workshops, webinars, and environmental fairs. Format the information as specified below, and provide a total of $n entries.

        **IMPORTANT:** Adhere to the character limits strictly.
        - title: (must be 40 characters or less)
        - content: (must be 500 characters or less)
        - category: "EVENTS"
        - publishedAt: The date of creation from the source (e.g., data form: "2023-10-27 15:30:40"). If you cannot find published date, just return null for this field.
        - source: URL of the source

        Ensure all information is the latest available as of today and is based on Canada or USA. Provide only information with clear sources; do not generate or fabricate sources. All fields are mandatory.
    """.trimIndent()
        }


        val requestJson = """
        {
          "contents": [
            {
              "parts": [
                {"text": "$promptText"}
              ]
            }
          ]
        }
    """.trimIndent()

        val requestBody = requestJson.toRequestBody("application/json".toMediaTypeOrNull())
        val request = Request.Builder()
            .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=$apiKey")
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw RuntimeException("Unexpected code $response")

            val responseBody = response.body?.string()
            val responseJson = mapper.readTree(responseBody)
            val newsletterList = mutableListOf<Newsletter>()

            responseJson["candidates"]?.forEach { candidate ->
                val parts = candidate["content"]?.get("parts")
                parts?.forEach { part ->
                    val textParts = part["text"]?.asText()?.split("\n")
                    if (textParts != null && textParts.size >= 5) {
                        val title = textParts[0].substringAfter(": ")
                        val content = textParts[1].substringAfter(": ")
                        val categoryStr = textParts[2].substringAfter(": ")
                        val publishedAtStr = textParts[3].substringAfter(": ")
                        val source = textParts[4].substringAfter(": ")

                        val publishedAt =
                            ZonedDateTime.parse(publishedAtStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

                        val newsletter = Newsletter(
                            title = title,
                            content = content,
                            category = NewsletterCategory.valueOf(categoryStr),
                            source = source,
                            publishedAt = publishedAt
                        )

                        newsletterList.add(newsletter)
                    }
                }
            }
            return newsletterList
        }
    }
}