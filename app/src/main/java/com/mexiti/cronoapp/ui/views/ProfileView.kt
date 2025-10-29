package com.mexiti.cronoapp.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.mexiti.cronoapp.ui.components.MainIconButton
import com.mexiti.cronoapp.ui.components.MainTextField
import com.mexiti.cronoapp.ui.components.MainTitle
import com.mexiti.cronoapp.viewmodel.AppDataViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(
    dataVM: AppDataViewModel,
    onBack: () -> Unit
) {
    val p by dataVM.profile.collectAsState()

    var nombre by remember(p) { mutableStateOf(p.nombre) }
    var ciudad by remember(p) { mutableStateOf(p.ciudad) }
    var sexo by remember(p) { mutableStateOf(p.sexo) }
    var gustos by remember(p) { mutableStateOf(p.gustos) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { MainTitle(title = "Perfil de Usuario") },
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
        Column(
            modifier = Modifier
                .padding(pad)
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
            Text(
                text = "Actualiza tus datos personales",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )

            // Campos reutilizando tu estilo
            MainTextField(value = nombre, onValueChange = { nombre = it }, label = "Nombre")
            MainTextField(value = ciudad, onValueChange = { ciudad = it }, label = "Ciudad")
            MainTextField(value = sexo, onValueChange = { sexo = it }, label = "Sexo")
            MainTextField(value = gustos, onValueChange = { gustos = it }, label = "Gustos musicales")

            // Botón de guardar
            Button(
                onClick = {
                    dataVM.saveProfile(nombre, ciudad, sexo, gustos)
                    onBack()
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
}
