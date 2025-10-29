package com.mexiti.cronoapp.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mexiti.cronoapp.R
import com.mexiti.cronoapp.data.DataSource
import com.mexiti.cronoapp.ui.components.CronCard
import com.mexiti.cronoapp.ui.components.FloatButton
import com.mexiti.cronoapp.ui.components.FloatButtonMinus
import com.mexiti.cronoapp.ui.components.MainTitle
import com.mexiti.cronoapp.ui.components.PlatilloItem
import com.mexiti.cronoapp.ui.components.formatTiempo
import com.mexiti.cronoapp.viewmodel.AppDataViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController, dataVM: AppDataViewModel) {
    Scaffold(
        topBar = {

            CenterAlignedTopAppBar(
                title = { MainTitle(title = stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.End
            ) {
                FloatButtonMinus {
                    navController.navigate("ProfileView")
                }
                FloatButton {
                    navController.navigate("AddView")
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.onPrimary
    ) { paddingValues ->
        ContentHomeView(paddingValues, navController, dataVM)
    }
}

@Composable
fun ContentHomeView(
    paddingValues: PaddingValues,
    navController: NavController,
    dataVM: AppDataViewModel
) {
    val dataList by dataVM.cronoList.collectAsState()
    val platillos = DataSource().loadPlatillos()

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 12.dp)
    ) {
        // ========= Sección de Cronómetros =========
        items(dataList) { item ->
            val delete = SwipeAction(
                icon = rememberVectorPainter(image = Icons.Default.Delete),
                background = MaterialTheme.colorScheme.error,
                onSwipe = { dataVM.deleteCrono(item) }
            )

            SwipeableActionsBox(
                startActions = listOf(delete),
                swipeThreshold = 150.dp
            ) {
                CronCard(
                    title = item.title,
                    crono = formatTiempo(time = item.crono)
                ) {
                    navController.navigate("EditView/${item.id}")
                }
            }
        }

        // Espacio entre secciones
        item { Spacer(modifier = Modifier.height(32.dp)) }

        // ========= Encabezado de la sección de recomendados =========
        item {
            Text(
                text = "Recomendados para ti",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        // ========= Lista de platillos =========
        items(platillos) { platillo ->
            PlatilloItem(
                platillo = platillo,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        // Espacio inferior para los FABs
        item { Spacer(modifier = Modifier.height(88.dp)) }
    }
}
