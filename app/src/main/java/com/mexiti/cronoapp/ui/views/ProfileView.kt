package com.mexiti.cronoapp.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mexiti.cronoapp.viewmodel.AppDataViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(
    dataVM: AppDataViewModel,
    onBack: () -> Unit
) {
    val p by dataVM.profile.collectAsState()

    val (nombre, setNombre) = remember(p) { mutableStateOf(p.nombre) }
    val (ciudad, setCiudad) = remember(p) { mutableStateOf(p.ciudad) }
    val (sexo, setSexo)     = remember(p) { mutableStateOf(p.sexo) }
    val (gustos, setGustos) = remember(p) { mutableStateOf(p.gustos) }

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Perfil de usuario") }) }
    ) { pad ->
        Column(
            modifier = Modifier.padding(pad).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = nombre, onValueChange = setNombre,
                label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = ciudad, onValueChange = setCiudad,
                label = { Text("Ciudad") }, modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = sexo, onValueChange = setSexo,
                label = { Text("Sexo") }, modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = gustos, onValueChange = setGustos,
                label = { Text("Gustos musicales") }, modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    dataVM.saveProfile(nombre, ciudad, sexo, gustos)
                    onBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Guardar") }
        }
    }
}
