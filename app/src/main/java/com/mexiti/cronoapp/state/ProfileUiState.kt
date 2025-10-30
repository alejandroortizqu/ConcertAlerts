
package com.mexiti.cronoapp.state

import com.mexiti.cronoapp.room.ProfileEntity

// Sealed interface to represent the different states of the Profile screen UI
sealed interface ProfileUiState {
    object Loading : ProfileUiState
    data class Success(val profile: ProfileEntity) : ProfileUiState
    data class Error(val message: String) : ProfileUiState
}
