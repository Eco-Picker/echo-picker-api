package com.eco_picker.api.global.support

import com.eco_picker.api.global.data.FileItem
import com.eco_picker.api.global.data.properties.FileProperties
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Service
class FileManager(private val fileProperties: FileProperties) {
    private val uploadDir: Path = Paths.get("uploads")
    private val maxFileSizeBytes: Long
        get() = fileProperties.maxFileSizeMb * 1024 * 1024 // mb to byte


    init {
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir)
        }
    }

    fun saveFile(file: MultipartFile): FileItem? {
        if (file.size > maxFileSizeBytes) {
            throw IllegalArgumentException("File size exceeds the maximum limit of 10MB")
        }

        val filename = file.originalFilename
        if (filename.isNullOrEmpty()) {
            throw Exception("File name must not be null or empty")
        }

        val targetLocation: Path = uploadDir.resolve(filename)
        try {
            file.inputStream.use { inputStream ->
                Files.copy(inputStream, targetLocation)
            }
        } catch (e: IOException) {
            throw RuntimeException("Could not save file: ${e.message}")
        }

        return FileItem(
            id = 1L,
            filename = filename,
            path = targetLocation.toString(),
        )
    }

    fun deleteFile(fileId: Long): Boolean {
        return true
    }
}