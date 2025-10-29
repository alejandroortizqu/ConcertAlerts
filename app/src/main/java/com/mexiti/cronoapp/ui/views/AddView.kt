/*package com.mexiti.cronoapp.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
fun ContentAddView(
    it: PaddingValues,
    navController: NavController,
    cronometroVM: CronometroViewModel,
    dataVM: AppDataViewModel
) {
    val state = cronometroVM.state
    LaunchedEffect(key1 = state.cronometroActivo) {
        cronometroVM.cronos()
    }
    Column(
        modifier = Modifier
            .padding(it)
            .padding(top = 30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = formatTiempo(time = cronometroVM.time),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground // 🔹 Texto principal adaptado al tema
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(vertical = 16.dp)
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
            CircleButton(
                icon = painterResource(id = R.drawable.stop_24),
                enabled = !state.cronometroActivo
            ) {
                cronometroVM.detener()
            }
            CircleButton(
                icon = painterResource(id = R.drawable.save_24),
                enabled = state.showSaveButton
            ) {
                cronometroVM.showTextField()
            }
        }

        if (state.showTextField) {
            MainTextField(
                value = state.title,
                onValueChange = { cronometroVM.onValue(it) },
                label = "Title"
            )

            Button(
                onClick = {
                    dataVM.addCrono(
                        Cronos(
                            title = state.title,
                            crono = cronometroVM.time
                        )
                    )
                    cronometroVM.detener()
                    navController.popBackStack()
                },
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary, // 🔹 Fondo botón
                    contentColor = MaterialTheme.colorScheme.onPrimary   // 🔹 Texto del botón
                )
            ) {
                Text(
                    text = "Guardar",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

// =======================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddView(
    navController: NavController,
    cronometroVM: CronometroViewModel,
    dataVM: AppDataViewModel
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    MainTitle(title = stringResource(R.string.add_view))
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary, // 🔹 Barra superior
                    titleContentColor = MaterialTheme.colorScheme.onPrimary // 🔹 Texto en barra
                ),
                navigationIcon = {
                    MainIconButton(icon = Icons.Default.ArrowBack) {
                        navController.popBackStack()
                    }
                }
            )
        }
    ) {
        ContentAddView(
            it = it,
            navController = navController,
            cronometroVM = cronometroVM,
            dataVM = dataVM
        )
    }
}

@Preview
@Composable
fun AddViewPreview() {
    //AddView()
}


*/



package com.mexiti.cronoapp.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons

import androidx.compose.material3.Button

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.mexiti.cronoapp.viewmodel.CronometroViewModel



// Importaciones requeridas (asegúrate de que estas estén al inicio de tu archivo principal)
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.mexiti.cronoapp.model.ConciertoItem
import org.jetbrains.annotations.VisibleForTesting
import java.text.NumberFormat

import androidx.compose.runtime.getValue

import androidx.compose.runtime.setValue


import androidx.navigation.NavController
import com.mexiti.cronoapp.viewmodel.AppDataViewModel
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import com.mexiti.cronoapp.ui.components.MainIconButton


// ... (otras importaciones que ya tenías)
// Remplaza el código anterior de CostGasLayout y las funciones de apoyo.

// =======================================================
// Nuevo Data Class para representar un artículo comprado
// =======================================================


// =======================================================
// Composable principal: CalculatorLayout
// =======================================================

// Dentro de tu archivo de vistas (AddView.kt o similar)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorView( // 👈 ¡Firma ajustada!
    navController: NavController,
    dataVM: AppDataViewModel // ✅ Ahora recibe AppDataViewModel
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    // Texto simple en lugar de stringResource si R.string falla
                    Text(text = "Calculadora Concierto")
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    // Asegúrate de que MainIconButton exista, si no, usa IconButton y Icon
                    MainIconButton(icon = Icons.Default.ArrowBack) {
                        navController.popBackStack()
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
        ) {
            // CalculatorLayout es la lógica de la calculadora
            CalculatorLayout()
        }
    }
}

// ... todo el código de CalculatorLayout, ProductItemCard, etc., se queda igual




@Composable
fun CalculatorLayout() {
    // 🔹 Estado para la lista de productos comprados
    var listaProductos by remember {
        mutableStateOf(listOf<ConciertoItem>())
    }
    // 🔹 Estado para el nuevo producto que se está agregando
    var nuevoNombre by remember { mutableStateOf("") }
    var nuevoPrecio by remember { mutableStateOf("") }
    var nextId by remember { mutableStateOf(1) } // Contador para IDs

    // 🔹 Cálculo del total
    val total = calcularMontoTotal(listaProductos)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(15.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🛒 Calculadora de Compras en Concierto",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        // ----------------------------------------------------
        // 1. Sección para agregar nuevos productos
        // ----------------------------------------------------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField( // Campo para el nombre del producto
                value = nuevoNombre,
                onValueChange = { nuevoNombre = it },
                label = { Text("Nombre del Producto") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )

            OutlinedTextField( // Campo para el precio del producto
                value = nuevoPrecio,
                onValueChange = { nuevoPrecio = it.replace(',', '.') },
                label = { Text("Precio (€ / $)") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.weight(0.7f).padding(end = 8.dp)
            )

            // Botón para agregar
            Button(
                onClick = {
                    val price = nuevoPrecio.toDoubleOrNull()
                    if (nuevoNombre.isNotBlank() && price != null && price >= 0) {
                        val newItem = ConciertoItem(
                            id = nextId++,
                            nombre = nuevoNombre,
                            precio = price
                        )
                        // Agregar el nuevo producto a la lista
                        listaProductos = listaProductos + newItem
                        // Limpiar campos para la próxima entrada
                        nuevoNombre = ""
                        nuevoPrecio = ""
                    }
                },
                enabled = nuevoNombre.isNotBlank() && nuevoPrecio.toDoubleOrNull() != null
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        // ----------------------------------------------------
        // 2. Sección de Lista de Compras (LazyColumn)
        // ----------------------------------------------------
        LazyColumn(
            modifier = Modifier.weight(1f) // Esto permite que el LazyColumn ocupe el espacio restante
        ) {
            items(listaProductos, key = { it.id }) { item ->
                ProductItemCard(
                    item = item,
                    onDelete = { itemToDelete ->
                        // Eliminar el producto de la lista
                        listaProductos = listaProductos.filter { it.id != itemToDelete.id }
                    }
                )
            }
        }

        // ----------------------------------------------------
        // 3. Monto Total
        // ----------------------------------------------------
        Text(
            text = "Monto Total: ${NumberFormat.getCurrencyInstance().format(total)}",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.primary
        )

    }
}


// =======================================================
// Composable para un Item de la Lista
// =======================================================
@Composable
fun ProductItemCard(item: ConciertoItem, onDelete: (ConciertoItem) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.nombre, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                Text(
                    text = NumberFormat.getCurrencyInstance().format(item.precio),
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            // Botón para eliminar
            IconButton(onClick = { onDelete(item) }) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = Color.Red
                )
            }
        }
    }
}


// =======================================================
// Función de Lógica de Cálculo
// =======================================================
@VisibleForTesting
internal fun calcularMontoTotal(items: List<ConciertoItem>): Double {
    // Suma todos los precios de la lista
    return items.sumOf { it.precio }
}

// -------------------------------------------------------
// Reemplaza CostGasLayout() con CalculatorLayout() en MainActivity:
// -------------------------------------------------------
/*
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CostoGasolinaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorLayout() // 👈 Llama al nuevo Composable
                }
            }
        }
    }
}
*/




