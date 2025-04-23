package com.example.princesstreatment.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.princesstreatment.ui.components.form.DateTimeSelector
import com.example.princesstreatment.ui.components.form.RecurrenceUnitSelector
import com.example.princesstreatment.viewmodel.EventViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventScreen(
    navController: NavController,
    viewModel: EventViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Form state
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedDateTime by remember {
        mutableStateOf(
            LocalDateTime.now().plusHours(1).withMinute(0)
        )
    }
    var recurrenceValue by remember { mutableStateOf("1") }
    var selectedRecurrenceUnit by remember { mutableStateOf(ChronoUnit.DAYS) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create New Event") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (validateForm(title, recurrenceValue)) {
                        val interval = recurrenceValue.toLongOrNull() ?: 1L
                        viewModel.addEvent(
                            title = title,
                            description = description,
                            nextOccurrence = selectedDateTime,
                            recurrenceUnit = selectedRecurrenceUnit,
                            recurrenceInterval = interval
                        )
                        navController.popBackStack()
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar("Please fill all required fields")
                        }
                    }
                }
            ) {
                Icon(Icons.Filled.Check, contentDescription = "Save Event")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = WindowInsets.systemBars.asPaddingValues().calculateTopPadding())
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title Field
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title*") },
                modifier = Modifier.fillMaxWidth(),
                isError = title.isEmpty()
            )

            // Description Field
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            // Date/Time Selector
            DateTimeSelector(
                selectedDateTime = selectedDateTime,
                onDateTimeSelected = { selectedDateTime = it }
            )

            // Recurrence Unit Selector
            RecurrenceUnitSelector(
                recurrenceValue = recurrenceValue,
                onRecurrenceValueChange = { recurrenceValue = it },
                selectedUnit = selectedRecurrenceUnit,
                onUnitSelected = { selectedRecurrenceUnit = it }
            )
        }
    }
}

private fun validateForm(title: String, recurrenceValue: String): Boolean {
    return title.isNotBlank() &&
            recurrenceValue.isNotBlank() &&
            recurrenceValue.toIntOrNull()?.let { it > 0 } == true
}