package com.mexiti.cronoapp.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mexiti.cronoapp.ui.components.AccountFloatButton
import com.mexiti.cronoapp.ui.components.ConcertList
import com.mexiti.cronoapp.ui.components.FloatButton
import com.mexiti.cronoapp.ui.components.MainTopBar
import com.mexiti.cronoapp.state.ConcertUiState
import com.mexiti.cronoapp.viewmodel.ConcertViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    navController: NavController,
    dataVM: ConcertViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            MainTopBar(
                title = "Concert App",
                showBackButton = false
            ) { }
        },
        floatingActionButton = {
            Column {
                FloatButton {
                    navController.navigate("CalculatorView")
                }
                Spacer(modifier = Modifier.height(8.dp))
                AccountFloatButton {
                    navController.navigate("ProfileView")
                }
            }
        }
    ) { innerPadding ->
        // Collect the entire UI state object
        val uiState by dataVM.uiState.collectAsState()

        // Use a when expression to display the correct UI for each state
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (uiState) {
                is ConcertUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is ConcertUiState.Success -> {
                    // Cast the state and pass the concert list to the composable
                    val concerts = (uiState as ConcertUiState.Success).concerts
                    ConcertList(concerts)
                }
                is ConcertUiState.Error -> {
                    // Show an error message
                    val message = (uiState as ConcertUiState.Error).message
                    Text(
                        text = "Error: $message",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}
