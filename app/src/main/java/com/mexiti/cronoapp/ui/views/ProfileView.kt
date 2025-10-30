
package com.mexiti.cronoapp.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mexiti.cronoapp.room.ProfileEntity // Corrected import
import com.mexiti.cronoapp.state.ProfileUiState // Corrected import
import com.mexiti.cronoapp.ui.components.MainIconButton
import com.mexiti.cronoapp.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(
    profileVM: ProfileViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Perfil de Usuario",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    MainIconButton(icon = Icons.Default.ArrowBack) {
                        onBack()
                    }
                }
            )
        }
    ) { pad ->
        val uiState by profileVM.uiState.collectAsState()

        Box(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is ProfileUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is ProfileUiState.Success -> {
                    ProfileForm(state.profile, profileVM)
                }
                is ProfileUiState.Error -> {
                    Text(text = "Error: ${state.message}")
                }
            }
        }
    }
}

@Composable
fun ProfileForm(profile: ProfileEntity, profileVM: ProfileViewModel) {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 32.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Actualiza tus datos personales",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )

        OutlinedTextField(
            value = profile.nombre,
            onValueChange = { profileVM.onProfileChange(nombre = it) }, // Corrected call
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = profile.ciudad,
            onValueChange = { profileVM.onProfileChange(ciudad = it) }, // Corrected call
            label = { Text("Ciudad") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = profile.sexo,
            onValueChange = { profileVM.onProfileChange(sexo = it) }, // Corrected call
            label = { Text("Sexo") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = profile.gustos,
            onValueChange = { profileVM.onProfileChange(gustos = it) }, // Corrected call
            label = { Text("Gustos musicales") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                profileVM.saveOrUpdateProfile()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Guardar Cambios",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
