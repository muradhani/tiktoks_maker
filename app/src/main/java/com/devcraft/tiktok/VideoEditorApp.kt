package com.devcraft.tiktok

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.devcraft.tiktok.features.screen.EditorScreen
import com.devcraft.tiktok.features.screen.HomeScreen
import com.devcraft.tiktok.features.screen.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoEditorApp() {
    var selectedVideoUri by remember { mutableStateOf<Uri?>(null) }
    var currentScreen by remember { mutableStateOf("home") }

    Scaffold(
        topBar = { VideoEditorTopBar() },
        bottomBar = { VideoEditorBottomBar(currentScreen) { currentScreen = it } }
    ) { innerPadding ->
        VideoEditorContent(
            currentScreen = currentScreen,
            selectedVideoUri = selectedVideoUri,
            onVideoSelected = { selectedVideoUri = it },
            onEditSelected = { currentScreen = "editor" },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VideoEditorTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                "Video Editor",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Composable
private fun VideoEditorBottomBar(
    currentScreen: String,
    onScreenSelected: (String) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(painterResource(R.drawable.ic_home), contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentScreen == "home",
            onClick = { onScreenSelected("home") }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(R.drawable.ic_edit), contentDescription = "Editor") },
            label = { Text("Editor") },
            selected = currentScreen == "editor",
            onClick = { onScreenSelected("editor") }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(R.drawable.ic_settings), contentDescription = "Settings") },
            label = { Text("Settings") },
            selected = currentScreen == "settings",
            onClick = { onScreenSelected("settings") }
        )
    }
}

@Composable
private fun VideoEditorContent(
    currentScreen: String,
    selectedVideoUri: Uri?,
    onVideoSelected: (Uri) -> Unit,
    onEditSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        when (currentScreen) {
            "home" -> HomeScreen(
                onVideoSelected = onVideoSelected,
                onEditSelected = onEditSelected
            )
            "editor" -> EditorScreen(selectedVideoUri)
            "settings" -> SettingsScreen()
        }
    }
}
