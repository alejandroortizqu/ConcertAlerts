package com.mexiti.cronoapp.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mexiti.cronoapp.R
import com.mexiti.cronoapp.model.Cronos
import com.mexiti.cronoapp.ui.components.CircleButton
import com.mexiti.cronoapp.ui.components.MainIconButton
import com.mexiti.cronoapp.ui.components.MainTextField
import com.mexiti.cronoapp.ui.components.MainTitle
import com.mexiti.cronoapp.ui.components.formatTiempo
import com.mexiti.cronoapp.viewmodel.AppDataViewModel
import com.mexiti.cronoapp.viewmodel.CronometroViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditView(
    navController: NavController,
    cronometroVM: CronometroViewModel,
    dataVM: AppDataViewModel,
    id: Long
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { MainTitle(title = stringResource(R.string.edit_cronometro)) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    MainIconButton(
                        icon = Icons.Default.ArrowBack
                    ) {
                        navController.popBackStack()
                    }
                }
            )
        }
    ) {
        ContentEditView(it, navController, cronometroVM, dataVM, id)
    }
}

@Composable
fun ContentEditView(
    it: PaddingValues,
    navController: NavController,
    cronometroVM: CronometroViewModel,
    dataVM: AppDataViewModel,
    id: Long
) {
    val state = cronometroVM.state

    /*LaunchedEffect(id) {
        cronometroVM.getCronoById(id)
    }*/

    Column(
        modifier = Modifier
            .padding(it)
            .padding(horizontal = 24.dp, vertical = 30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Tiempo principal
        Text(
            text = formatTiempo(time = cronometroVM.time),
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botones de control
        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleButton(
                icon = painterResource(id = R.drawable.play_arrow_24),
                enabled = !state.cronometroActivo
            ) {
                cronometroVM.iniciar()
            }
            CircleButton(
                icon = painterResource(id = R.drawable.pause_24),
                enabled = state.cronometroActivo
            ) {
                cronometroVM.pausar()
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Campo de texto para título
        MainTextField(
            value = state.title,
            onValueChange = { cronometroVM.onValue(it) },
            label = "Título"
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de guardar cambios
        /*Button(
            onClick = {
                if (state.title.isNotBlank()) {
                    dataVM.updateCrono(
                        Cronos(
                            id = id,
                            title = state.title,
                            crono = cronometroVM.time
                        )
                    )
                    navController.popBackStack()
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier
                .padding(top = 12.dp)
        ) {
            Text(
                text = "Guardar Cambios",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }*/

        DisposableEffect(Unit) {
            onDispose {
                cronometroVM.detener()
            }
        }
    }
}
