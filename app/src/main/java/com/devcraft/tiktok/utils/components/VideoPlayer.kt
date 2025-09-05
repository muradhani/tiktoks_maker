package com.devcraft.tiktok.utils.components

import android.net.Uri
import android.view.SurfaceView
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.AudioAttributes
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun VideoPlayer(
    uri: Uri,
    modifier: Modifier = Modifier,
    playWhenReady: Boolean = true,
    loop: Boolean = true
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Create ExoPlayer (once per URI)
    val exoPlayer = remember(uri) {
        ExoPlayer.Builder(context).build().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(C.USAGE_MEDIA)
                    .setContentType(C.CONTENT_TYPE_MOVIE)
                    .build(),
                true
            )
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            this.playWhenReady = playWhenReady
            if (loop) repeatMode = Player.REPEAT_MODE_ONE
        }
    }

    // Listen for READY state to trigger audio fade-in
    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_READY && exoPlayer.currentPosition == 0L) {
                    // Start with muted volume
                    exoPlayer.volume = 0f
                    // Smooth fade-in
                    scope.launch {
                        for (i in 1..10) {
                            exoPlayer.volume = i / 10f
                            delay(100) // fade over 1s
                        }
                    }
                }
            }
        }
        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    // Video output surface only (no built-in UI)
    AndroidView(
        factory = { ctx ->
            SurfaceView(ctx).apply {
                exoPlayer.setVideoSurfaceView(this)
            }
        },
        modifier = modifier
    )
}
