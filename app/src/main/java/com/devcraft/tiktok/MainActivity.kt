package com.devcraft.tiktok

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.videoeditor.ui.theme.VideoEditorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VideoEditorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VideoEditorApp()
                }
            }
        }
    }
}

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


@Composable
fun HomeScreen(
    onVideoSelected: (Uri) -> Unit,
    onEditSelected: () -> Unit
) {
    val context = LocalContext.current
    val videoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> uri?.let { onVideoSelected(it) } }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Create Subway/GTA Style Videos",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedCard(
            onClick = {
                videoPicker.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_video),
                    contentDescription = "Select Video",
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Select Video", style = MaterialTheme.typography.titleLarge)
                Text("Choose a video from your gallery", style = MaterialTheme.typography.bodyMedium)
            }
        }

        FilledTonalButton(
            onClick = onEditSelected,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Start Editing", style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text("Features:", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        FeatureItem(
            icon = R.drawable.ic_subtitle,
            title = "TikTok-style Subtitles",
            description = "Add animated subtitles like in TikTok videos"
        )
        FeatureItem(
            icon = R.drawable.ic_filter,
            title = "GTA/Subway Filters",
            description = "Apply stylish filters inspired by games"
        )
        FeatureItem(
            icon = R.drawable.ic_export,
            title = "Export for TikTok",
            description = "Optimized export for social media"
        )
    }
}

@Composable
fun FeatureItem(icon: Int, title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = title,
            modifier = Modifier
                .size(40.dp)
                .padding(end = 16.dp)
        )
        Column {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun EditorScreen(selectedVideoUri: Uri?) {
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
                Text("Video Preview", style = MaterialTheme.typography.bodyLarge)
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

@Composable
fun FilterOption(name: String, icon: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(icon),
            contentDescription = name,
            modifier = Modifier.size(40.dp)
        )
        Text(name, style = MaterialTheme.typography.bodySmall)
    }
}

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

@Composable
fun SettingsItem(title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(description, style = MaterialTheme.typography.bodyMedium)
        }
        Switch(checked = false, onCheckedChange = {})
    }
}