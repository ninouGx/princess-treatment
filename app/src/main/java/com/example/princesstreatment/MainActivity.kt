package com.example.princesstreatment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.princesstreatment.ui.components.EventItem
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
                    // Other navigation destinations
                }
                /*
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }*/
            }
        }
    }
}

@Composable
fun EventsScreen(navController: NavController) {
    val viewModel: EventViewModel = hiltViewModel()
    val events = viewModel.allEvents.collectAsState().value

     LazyColumn {
        items(events.size) { index ->
            val event = events[index]
            EventItem(
                event = event,
                onMarkCompleted = { viewModel.markEventCompleted(event.id) }
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PrincessTreatmentTheme {
        Greeting("Android")
    }
}