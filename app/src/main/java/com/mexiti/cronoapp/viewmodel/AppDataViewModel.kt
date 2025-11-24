package com.mexiti.cronoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mexiti.cronoapp.model.ConciertoItem
import com.mexiti.cronoapp.model.Cronos
import com.mexiti.cronoapp.repository.ConciertoRepository
import com.mexiti.cronoapp.room.ProfileEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppDataViewModel @Inject constructor(
    private val repository: ConciertoRepository
) : ViewModel() {

    val itemList: StateFlow<List<ConciertoItem>> =
        repository.getAllItems().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )



    private val _profile = MutableStateFlow(ProfileEntity())
    val profile = _profile.asStateFlow()

    init {

        viewModelScope.launch(Dispatchers.IO) {
            repository.observeProfile().collect { p ->
                _profile.value = p ?: ProfileEntity()
            }
        }
    }


    // 2. Función para insertar
    fun insertItem(item: ConciertoItem) {
        viewModelScope.launch {
            repository.insertItem(item)
        }
    }

    // 3. Función para eliminar
    fun deleteItem(item: ConciertoItem) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }



    fun saveProfile(nombre: String, ciudad: String, sexo: String, gustos: String) =
        viewModelScope.launch {
            repository.saveProfile(
                ProfileEntity(
                    id = 0,
                    nombre = nombre,
                    ciudad = ciudad,
                    sexo = sexo,
                    gustos = gustos
                )
            )
        }
}
