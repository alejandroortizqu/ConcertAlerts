/*package com.mexiti.cronoapp.ui.views
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import com.mexiti.cronoapp.ui.components.MainIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorView(
    navController: NavController,
    dataVM: AppDataViewModel,
    conciertoVM: AppDataViewModel// recibe AppDataViewModel
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
            CalculatorLayout(conciertoVM = conciertoVM)
        }
    }
}


@Composable
fun CalculatorLayout(conciertoVM: AppDataViewModel) { // 👈 Recibe el VM

    // ❌ ELIMINAR O COMENTAR: La lista de estado temporal
    // var listaProductos by remember { mutableStateOf(listOf<ConciertoItem>()) }

    // 🟢 CARGAR DATOS DE ROOM: Usamos el Flow del ViewModel
    val listaProductos by conciertoVM.itemList.collectAsState(initial = emptyList())

    // Estado para la nueva entrada (se mantiene en memoria temporalmente)
    var nuevoNombre by remember { mutableStateOf("") }
    var nuevoPrecio by remember { mutableStateOf("") }
    // Ya no necesitas 'nextId' porque ROOM maneja el ID.

    // 🔹 Cálculo del total (usa la lista cargada de ROOM)
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

            // 2. Sección de Lista de Compras (LazyColumn)

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


            // 3. Monto Total
            Text(
                text = "Monto Total: ${NumberFormat.getCurrencyInstance().format(total)}",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.primary
            )

        }


}








/*@Composable
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

        // 2. Sección de Lista de Compras (LazyColumn)

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


        // 3. Monto Total
        Text(
            text = "Monto Total: ${NumberFormat.getCurrencyInstance().format(total)}",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.primary
        )

    }
}
*/


// Composable para un Item de la Lista

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


// Función de Lógica de Cálculo
@VisibleForTesting
internal fun calcularMontoTotal(items: List<ConciertoItem>): Double {
    // Suma todos los precios de la lista
    return items.sumOf { it.precio }
}



*/


// ui/views/CalculatorView.kt
package com.mexiti.cronoapp.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mexiti.cronoapp.model.ConciertoItem
import com.mexiti.cronoapp.ui.components.MainIconButton
import com.mexiti.cronoapp.viewmodel.AppDataViewModel
import java.text.NumberFormat
import org.jetbrains.annotations.VisibleForTesting


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorView(
    navController: NavController,
    dataVM: AppDataViewModel,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Calculadora Concierto") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
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
            // Pasa el ViewModel de ROOM al layout
            CalculatorLayout(dataVM = dataVM)
        }
    }
}


@Composable
fun CalculatorLayout(dataVM: AppDataViewModel) { // 👈 Recibe el VM de ROOM

    // 🟢 CARGAR DATOS DE ROOM: Usa el Flow del ViewModel de Concierto
    val listaProductos by dataVM.itemList.collectAsState(initial = emptyList())

    var nuevoNombre by remember { mutableStateOf("") }
    var nuevoPrecio by remember { mutableStateOf("") }

    //  Cálculo del total (lista cargada de ROOM)
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
                        // 🟢 USAR ROOM: Llamada a la función de insert del ViewModel
                        dataVM.insertItem(
                            ConciertoItem(
                                nombre = nuevoNombre,
                                precio = price
                            )
                        )
                        // Limpiar campos
                        nuevoNombre = ""
                        nuevoPrecio = ""
                    }
                },
                enabled = nuevoNombre.isNotBlank() && nuevoPrecio.toDoubleOrNull() != null
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        } // Fin del Row de entrada


        Divider(modifier = Modifier.padding(vertical = 8.dp))

        // ----------------------------------------------------
        // 2. Sección de Lista de Compras (LazyColumn)
        // ----------------------------------------------------
        LazyColumn(
            modifier = Modifier.weight(1f) // Esto permite que el LazyColumn ocupe el espacio restante
        ) {
            // Bucle que define 'item' y usa la lista de ROOM
            items(listaProductos, key = { it.id }) { item ->
                ProductItemCard(
                    item = item,
                    onDelete = { itemToDelete ->
                        // Llamada a la función de delete del ViewModel
                        dataVM.deleteItem(itemToDelete)
                    }
                )
            }
        }


        // 3. Monto Total
        Text(
            text = "Monto Total: ${NumberFormat.getCurrencyInstance().format(total)}",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}


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


//Prueba unitaria
internal fun calcularMontoTotal(items: List<ConciertoItem>): Double {
    // Suma todos los precios de la lista
    return items.sumOf { it.precio }
}








