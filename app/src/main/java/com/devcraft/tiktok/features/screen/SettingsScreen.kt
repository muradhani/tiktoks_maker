package com.devcraft.tiktok.features.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devcraft.tiktok.SettingsItem

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Settings",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        SettingsItem(
            title = "Video Quality",
            description = "Set output video quality"
        )
        SettingsItem(
            title = "Default Style",
            description = "Set your default video style"
        )
        SettingsItem(
            title = "Auto-save Projects",
            description = "Automatically save your editing progress"
        )
        SettingsItem(
            title = "TikTok Integration",
            description = "Connect to your TikTok account"
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            "Video Editor v1.0",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}