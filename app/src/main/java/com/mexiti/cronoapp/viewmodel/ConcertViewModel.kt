
package com.mexiti.cronoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mexiti.cronoapp.data.DataSource
import com.mexiti.cronoapp.state.ConcertUiState // Import the new state file
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConcertViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<ConcertUiState>(ConcertUiState.Loading)
    val uiState: StateFlow<ConcertUiState> = _uiState.asStateFlow()

    init {
        loadConcerts()
    }

    private fun loadConcerts() {
        viewModelScope.launch {
            _uiState.value = ConcertUiState.Loading // Set loading state before trying
            try {
                // In the future, this could be a network or database call that might fail.
                val concerts = DataSource().loadConcerts()
                _uiState.value = ConcertUiState.Success(concerts)
            } catch (e: Exception) {
                // If any error occurs, capture it and set the Error state.
                _uiState.value = ConcertUiState.Error("Failed to load concerts: ${e.message}")
            }
        }
    }
}
