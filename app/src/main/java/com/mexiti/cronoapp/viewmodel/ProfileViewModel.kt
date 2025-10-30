
package com.mexiti.cronoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mexiti.cronoapp.repository.ProfileRepository
import com.mexiti.cronoapp.room.ProfileEntity
import com.mexiti.cronoapp.state.ProfileUiState // Import the new state file
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.observeProfile().collect { profile ->
                    val currentProfile = profile ?: ProfileEntity(
                        id = 0,
                        nombre = "Anonimo",
                        ciudad = "Ciudad Desconocida",
                        sexo = "No especificado",
                        gustos = "Musica"
                    )
                    _uiState.value = ProfileUiState.Success(currentProfile)
                }
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error(e.message ?: "Error desconocido al cargar el perfil")
            }
        }
    }

    fun onProfileChange(nombre: String? = null, ciudad: String? = null, sexo: String? = null, gustos: String? = null) {
        val currentState = _uiState.value
        if (currentState is ProfileUiState.Success) {
            val updatedProfile = currentState.profile.copy(
                nombre = nombre ?: currentState.profile.nombre,
                ciudad = ciudad ?: currentState.profile.ciudad,
                sexo = sexo ?: currentState.profile.sexo,
                gustos = gustos ?: currentState.profile.gustos
            )
            _uiState.value = ProfileUiState.Success(updatedProfile)
        }
    }

    fun saveOrUpdateProfile() {
        val currentState = _uiState.value
        if (currentState is ProfileUiState.Success) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repository.saveProfile(currentState.profile)
                } catch (e: Exception) {
                    println("Failed to save profile: ${e.message}")
                }
            }
        }
    }
}
