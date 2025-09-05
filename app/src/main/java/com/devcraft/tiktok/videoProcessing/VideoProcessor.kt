
package com.devcraft.tiktok.videoProcessing

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.transformer.Composition
import androidx.media3.transformer.EditedMediaItem
import androidx.media3.transformer.ExportResult
import androidx.media3.transformer.Transformer
import androidx.media3.common.MimeTypes
import androidx.media3.common.util.UnstableApi
import androidx.media3.effect.RgbAdjustment
import androidx.media3.effect.ScaleAndRotateTransformation
import androidx.media3.transformer.Effects
import androidx.media3.transformer.ExportException
import androidx.media3.transformer.TransformationRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

@OptIn(UnstableApi::class)

class VideoProcessor(private val context: Context) {
    suspend fun processVideo(
        inputUri: Uri,
        effects: List<VideoEffect>,
        onProgress: (Float) -> Unit
    ): Result<Uri> = withContext(Dispatchers.IO) {
        try {
            // Create output file
            val outputFile = createOutputFile(context)

            // Create enhanced listener
            val listener = createTransformerListener(onProgress)

            // Build transformation
            val transformer = Transformer.Builder(context)
                .setVideoMimeType(MimeTypes.VIDEO_H264)
                .addListener(listener)
                .build()

            // Create media item from URI
            val mediaItem = MediaItem.fromUri(inputUri)

            // Create edited media item with effects
            val editedMediaItem = EditedMediaItem.Builder(mediaItem)
                .setEffects(createEffects(effects))
                .build()

            // Start transformation
            transformer.start(editedMediaItem, outputFile.absolutePath)

            if (outputFile.exists() && outputFile.length() > 0) {
                Result.success(Uri.fromFile(outputFile))
            } else {
                Result.failure(Exception("Video processing failed - no output file created"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun createEffects(effects: List<VideoEffect>): Effects {
        val videoEffects = effects.map { effect ->
            when (effect.type) {
                VideoEffectType.GTA -> createGtaEffect()
                VideoEffectType.SUBWAY -> createSubwayEffect()
                VideoEffectType.TIKTOK -> createTikTokEffect()
                else -> ScaleAndRotateTransformation.Builder().build()
            }
        }

        return Effects(emptyList(),videoEffects)
    }

    private fun createGtaEffect(): RgbAdjustment {
        return RgbAdjustment.Builder()
            .setRedScale(1.2f)  // Boost reds
            .setGreenScale(0.9f) // Reduce greens
            .setBlueScale(0.9f)  // Reduce blues
            .build()
    }

    private fun createSubwayEffect(): RgbAdjustment {
        return RgbAdjustment.Builder()
            .setRedScale(1.1f)
            .setGreenScale(0.95f)
            .setBlueScale(1.05f)
            .build()
    }

    private fun createTikTokEffect(): RgbAdjustment {
        return RgbAdjustment.Builder()
            .setRedScale(1.15f)
            .setGreenScale(1.05f)
            .setBlueScale(0.95f)
            .build()
    }

    private fun createTransformerListener(onProgress: (Float) -> Unit): Transformer.Listener {
        return object : Transformer.Listener {
            override fun onCompleted(composition: Composition, result: ExportResult) {
            }

            override fun onError(
                composition: Composition,
                exportResult: ExportResult,
                exportException: ExportException
            ) {
                super.onError(composition, exportResult, exportException)
            }

        }
    }

    private fun createOutputFile(context: Context): File {
        val timeStamp = System.currentTimeMillis()
        val storageDir = context.getExternalFilesDir(null)
        return File.createTempFile(
            "edited_video_${timeStamp}",
            ".mp4",
            storageDir
        )
    }
}

enum class VideoEffectType {
    NONE, GTA, SUBWAY, TIKTOK
}

data class VideoEffect(
    val type: VideoEffectType,
    val intensity: Float = 1.0f
)