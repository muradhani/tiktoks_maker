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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.devcraft.tiktok.features.screen.EditorScreen
import com.devcraft.tiktok.features.screen.HomeScreen
import com.devcraft.tiktok.features.screen.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoEditorApp() {
    var selectedVideoUri by remember { mutableStateOf<Uri?>(null) }
    var currentScreen by remember { mutableStateOf("home") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
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
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(painterResource(R.drawable.ic_home), contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = currentScreen == "home",
                    onClick = { currentScreen = "home" }
                )
                NavigationBarItem(
                    icon = { Icon(painterResource(R.drawable.ic_edit), contentDescription = "Editor") },
                    label = { Text("Editor") },
                    selected = currentScreen == "editor",
                    onClick = { currentScreen = "editor" }
                )
                NavigationBarItem(
                    icon = { Icon(painterResource(R.drawable.ic_settings), contentDescription = "Settings") },
                    label = { Text("Settings") },
                    selected = currentScreen == "settings",
                    onClick = { currentScreen = "settings" }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen) {
                "home" -> HomeScreen(
                    onVideoSelected = { uri -> selectedVideoUri = uri },
                    onEditSelected = { currentScreen = "editor" }
                )
                "editor" -> EditorScreen(selectedVideoUri)
                "settings" -> SettingsScreen()
            }
        }
    }
}