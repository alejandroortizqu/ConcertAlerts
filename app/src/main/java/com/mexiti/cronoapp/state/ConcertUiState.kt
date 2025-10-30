
package com.mexiti.cronoapp.state

import com.mexiti.cronoapp.model.Concert

// Represents the different states for the Concerts screen UI.
sealed interface ConcertUiState {
    data class Success(val concerts: List<Concert>) : ConcertUiState
    data class Error(val message: String) : ConcertUiState
    object Loading : ConcertUiState
}
