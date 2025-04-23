package com.example.princesstreatment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.princesstreatment.ui.components.EventItem
import com.example.princesstreatment.ui.screens.CreateEventScreen
import com.example.princesstreatment.ui.theme.PrincessTreatmentTheme
import com.example.princesstreatment.viewmodel.EventViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrincessTreatmentTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "events") {
                    composable("events") {
                        EventsScreen(navController)
                    }
                    composable("create_event") {
                        CreateEventScreen(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun EventsScreen(navController: NavController) {
    val viewModel: EventViewModel = hiltViewModel()
    val events = viewModel.allEvents.collectAsState().value

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("create_event") }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Event")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            Text(
                text = "Princess Treatment Events",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(16.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(events.size) { index ->
                    val event = events[index]
                    EventItem(
                        event = event,
                        onMarkCompleted = { viewModel.markEventCompleted(event.id) },
                        onToggleActive = {
                            if (event.isActive) {
                                viewModel.deactivateEvent(event.id)
                            } else {
                                viewModel.activateEvent(event.id)
                            }
                        }
                    )
                }
            }
        }
    }
}