package com.mexiti.cronoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mexiti.cronoapp.data.DataSource // Asumo que esta clase carga los conciertos
import com.mexiti.cronoapp.model.ConciertoItem // Entidad de la Calculadora/ROOM
import com.mexiti.cronoapp.repository.ConciertoRepository
import com.mexiti.cronoapp.room.ProfileEntity
import com.mexiti.cronoapp.state.ConcertUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// ⚠️ Usamos Hilt para inyectar el Repositorio
@HiltViewModel
class ConcertViewModel @Inject constructor(
    private val repository: ConciertoRepository
) : ViewModel() {

    // ==========================================================
    // 1. ESTADO DE CARGA DE CONCIERTOS (Para HomeView)
    // ==========================================================
    private val _uiState = MutableStateFlow<ConcertUiState>(ConcertUiState.Loading)
    val uiState: StateFlow<ConcertUiState> = _uiState.asStateFlow()

    // ==========================================================
    // 2. ESTADO DEL PERFIL (Para ProfileView)
    // ==========================================================
    private val _profile = MutableStateFlow(ProfileEntity())
    // 🟢 Esta propiedad es la que lee ProfileView.kt con collectAsState()
    val profile: StateFlow<ProfileEntity> = _profile.asStateFlow()

    init {
        // Inicializa la carga de conciertos y la observación del perfil
        loadConcerts()
        observeProfileChanges()
    }

    // ==========================================================
    // 3. LÓGICA DE PERFIL (Para ProfileView)
    // ==========================================================

    /**
     * Observa los cambios en la base de datos y actualiza el StateFlow de perfil.
     */
    private fun observeProfileChanges() {
        viewModelScope.launch(Dispatchers.IO) {
            // Esto llama a repository.observeProfile()
            repository.observeProfile().collect { p ->
                _profile.value = p ?: ProfileEntity()
            }
        }
    }

    /**
     * Guarda la entidad de perfil en la base de datos (ROOM).
     * Esta es la función llamada por ProfileForm.
     */
    fun saveProfile(nombre: String, ciudad: String, sexo: String, gustos: String) =
        viewModelScope.launch {
            repository.saveProfile(
                ProfileEntity(
                    // Asumo que el ID es 0 o 1 si solo manejas un perfil
                    id = 0,
                    nombre = nombre,
                    ciudad = ciudad,
                    sexo = sexo,
                    gustos = gustos
                )
            )
        }

    // ⚠️ NOTA: Asumo que tienes funciones de onChange en tu ProfileViewModel
    // para manejar la actualización de los campos de texto, si no, usa el siguiente patrón:
    fun onProfileChange(nombre: String? = null, ciudad: String? = null, sexo: String? = null, gustos: String? = null) {
        _profile.update {
            it.copy(
                nombre = nombre ?: it.nombre,
                ciudad = ciudad ?: it.ciudad,
                sexo = sexo ?: it.sexo,
                gustos = gustos ?: it.gustos
            )
        }
    }


    // ==========================================================
    // 4. LÓGICA DE CARGA DE CONCIERTOS (Para HomeView)
    // ==========================================================
    private fun loadConcerts() {
        viewModelScope.launch {
            _uiState.value = ConcertUiState.Loading
            try {
                val concerts = DataSource().loadConcerts()
                _uiState.value = ConcertUiState.Success(concerts)
            } catch (e: Exception) {
                _uiState.value = ConcertUiState.Error("Failed to load concerts: ${e.message}")
            }
        }
    }
}


/*ackage com.mexiti.cronoapp.viewmodel

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
*/