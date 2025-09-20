package com.devcraft.tiktok.features.screen

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.devcraft.tiktok.R
import com.devcraft.tiktok.features.viewModel.EditorViewModel
import com.devcraft.tiktok.utils.components.FilterOption
import com.devcraft.tiktok.utils.components.VideoPlayer

@Composable
fun EditorScreen(
    selectedVideoUri: Uri?,
    viewModel: EditorViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (selectedVideoUri != null) {
            Text(
                "Video Editor",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Video preview would go here
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                VideoPlayer(
                    uri = selectedVideoUri,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Editing options
            Text("Add Subtitle", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                placeholder = { Text("Enter your subtitle text") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Style Options", style = MaterialTheme.typography.titleMedium)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FilterOption("GTA Style", R.drawable.ic_filter)
                FilterOption("Subway Style", R.drawable.ic_filter)
                FilterOption("TikTok Style", R.drawable.ic_filter)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { /* Export video */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Export Video for TikTok", style = MaterialTheme.typography.titleMedium)
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "No video selected",
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    "Please go back and select a video to edit",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
        }
    }
}
