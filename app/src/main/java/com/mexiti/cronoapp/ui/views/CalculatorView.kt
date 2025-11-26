// ui/views/CalculatorView.kt

package com.mexiti.cronoapp.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mexiti.cronoapp.ui.components.MainTopBar
import com.mexiti.cronoapp.model.ConciertoItem
import androidx.compose.ui.text.input.ImeAction
import com.mexiti.cronoapp.viewmodel.CalculatorViewModel
import java.text.NumberFormat
import org.jetbrains.annotations.VisibleForTesting


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorView(
    navController: NavController,
    dataVM: CalculatorViewModel
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Calculadora Concierto") },
                // 🟢 CORRECCIÓN: Añadir el icono de navegación aquí
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar a Home")
                    }
                }
            )
        }
    ) { innerPadding ->
        ContentCalculatorView(
            modifier = Modifier.padding(innerPadding),
            calculatorVM = dataVM
        )
    }
}
@Composable
fun ContentCalculatorView(
    modifier: Modifier,
    calculatorVM: CalculatorViewModel
){
    // 🟢 Recoge los dos StateFlows del VM
    val uiState by calculatorVM.uiState.collectAsState()
    val productList by calculatorVM.productList.collectAsState(initial = emptyList())

    var errorMessage by remember { mutableStateOf<String?>(null) }

    val total = productList.sumOf { it.precio }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = uiState.newProductName,
            onValueChange = { calculatorVM.onProductNameChange(it); errorMessage = null },
            label = { Text("Nombre del producto") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = uiState.newProductPrice,
            onValueChange = { calculatorVM.onProductPriceChange(it); errorMessage = null },
            label = { Text("Precio") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val price = uiState.newProductPrice.toDoubleOrNull()
                if (uiState.newProductName.isBlank() || price == null || price <= 0) {
                    errorMessage = "Datos inválidos o faltantes."
                } else {
                    // 🟢 LLAMADA CORREGIDA: Llama a addProduct() sin argumentos (el VM lee los inputs)
                    calculatorVM.addProduct()
                    errorMessage = null
                }
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Agregar")
        }

        errorMessage?.let { message ->
            Text(text = message, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(bottom = 8.dp))
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(productList, key = { it.id }) { producto ->
                Card(
                    modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = producto.nombre, fontWeight = FontWeight.Bold)
                            Text(text = String.format("$%.2f", producto.precio))
                        }
                        // 🟢 LLAMADA CORREGIDA: Pasa el objeto completo para eliminar (ROOM)
                        IconButton(onClick = { calculatorVM.removeProduct(producto) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                        }
                    }
                }
            }
        }

        val totalFormateado = String.format("Total: $%.2f", total)
        Text(text = totalFormateado, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 16.dp))
    }
}



/*// ui/views/CalculatorView.kt

package com.mexiti.cronoapp.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mexiti.cronoapp.ui.components.MainTopBar
import com.mexiti.cronoapp.model.ConciertoItem // Asumo que usas ConciertoItem o ProductItem
import androidx.compose.ui.text.input.ImeAction
import com.mexiti.cronoapp.viewmodel.CalculatorViewModel
import java.text.NumberFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorView(
    navController: NavController,
    dataVM: CalculatorViewModel // 👈 Recibe el VM de la calculadora
) {
    Scaffold(
        topBar = {
            MainTopBar(title = "Calculadora de compras en concierto") {
                navController.popBackStack()
            }
        }
    ) { innerPadding ->
        ContentCalculatorView(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            calculatorVM = dataVM // 👈 Pasa el VM de la calculadora
        )
    }
}

@Composable
fun ContentCalculatorView(
    modifier: Modifier,
    navController: NavController,
    calculatorVM: CalculatorViewModel
){
    // Estos valores deben estar en tu CalculatorViewModel, aquí solo se usan para la UI
    var newProductName by remember { mutableStateOf("") }
    var newProductPrice by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // 🟢 CORRECCIÓN: Carga de ROOM. productList debe ser una propiedad pública StateFlow<List<ConciertoItem>> en CalculatorViewModel
    val productList by calculatorVM.productList.collectAsState(initial = emptyList())

    val total = productList.sumOf { it.precio } // Asumo que ConciertoItem tiene una propiedad 'precio'

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = newProductName,
            onValueChange = { newProductName = it; errorMessage = null },
            label = { Text("Nombre del producto") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = newProductPrice,
            onValueChange = { newProductPrice = it.filter { c -> c.isDigit() || c == '.' }; errorMessage = null },
            label = { Text("Precio") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val price = newProductPrice.toDoubleOrNull()
                if (newProductName.isBlank() || price == null || price <= 0) {
                    errorMessage = "Datos inválidos o faltantes."
                } else {
                    // ❌ ELIMINAR O COMENTAR la línea donde se construye el objeto.
                    // val newProduct = ConciertoItem(nombre = newProductName, precio = price)

                    // 🟢 CORRECCIÓN: Llamar a addProduct() SIN argumentos
                    // El ViewModel se encargará de leer newProductName y newProductPrice.
                    calculatorVM.addProduct()

                    // Limpiar inputs
                    newProductName = ""
                    newProductPrice = ""
                    errorMessage = null
                }
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Agregar")
        }

        errorMessage?.let { message ->
            Text(text = message, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(bottom = 8.dp))
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(productList, key = { it.id }) { producto ->
                Card(
                    modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = producto.nombre, fontWeight = FontWeight.Bold)
                            Text(text = String.format("$%.2f", producto.precio))
                        }
                        // 🟢 CORRECCIÓN: Pasa el objeto completo para eliminar.
                        // Asumo que removeProduct(item: ConciertoItem) existe en tu VM.
                        IconButton(onClick = { calculatorVM.removeProduct(producto) }) {
                            Icon(Icons.Default.Delete, description = "Eliminar")
                        }
                    }
                }
            }
        }

        val totalFormteado = String.format("Total: $%.2f", total)
        Text(text = totalFormteado, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 16.dp))
    }
}
 */



/*package com.mexiti.cronoapp.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mexiti.cronoapp.ui.components.MainTopBar
import com.mexiti.cronoapp.model.ConciertoItem
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.ImeAction
import com.mexiti.cronoapp.viewmodel.CalculatorViewModel // 🟢 USADO: CalculatorViewModel
import java.text.NumberFormat
// CalculatorView.kt (CORRECCIÓN DE LA FUNCIÓN PRINCIPAL)
import com.mexiti.cronoapp.viewmodel.ConcertViewModel // Asegúrate de importar ambos VM


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorView(
    navController: NavController,
    // 🟢 Recibir AMBOS VMs de la navegación, aunque solo usemos uno para el contenido
    calculatorVM: CalculatorViewModel,
    concertVM: ConcertViewModel // Asumo que NavManager está pasando este segundo VM
) {
    Scaffold(
        topBar = { /* ... */ }
    ) { innerPadding ->
        // 🟢 PASAR SÓLO EL VM REQUERIDO (calculatorVM) a la función de contenido
        ContentCalculatorView(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            calculatorVM = calculatorVM // 👈 PASAMOS EL VM CORRECTO AQUÍ
        )
    }
}

// ====================================================================
@Composable
fun ContentCalculatorView(
    modifier: Modifier,
    navController: NavController,
    calculatorVM: CalculatorViewModel // 👈 Solo necesita el VM de la calculadora
){
    // Manejo de estado local
    var newProductName by remember { mutableStateOf("") }
    var newProductPrice by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // 🟢 CARGA DE ROOM: Usa la propiedad productList del CalculatorViewModel
    val productList by calculatorVM.productList.collectAsState(initial = emptyList())

    val total = productList.sumOf { it.precio }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Inputs de texto
        OutlinedTextField(
            value = newProductName,
            onValueChange = { newProductName = it; errorMessage = null },
            label = { Text("Nombre del producto") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = newProductPrice,
            onValueChange = { newProductPrice = it.filter { c -> c.isDigit() || c == '.' }; errorMessage = null },
            label = { Text("Precio") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            // 🟢 CORRECCIÓN 1: Llama a addProduct() sin argumentos
            onClick = {
                val price = newProductPrice.toDoubleOrNull()
                if (newProductName.isBlank() || price == null || price <= 0) {
                    errorMessage = "Datos inválidos o faltantes."
                } else {
                    // Llama a la función sin argumentos. La lógica de crear el objeto
                    // y guardarlo se moverá al ViewModel.
                    calculatorVM.addProduct()
                    newProductName = ""
                    newProductPrice = ""
                    errorMessage = null
                }
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Agregar")
        }

        errorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            // Itera sobre la lista cargada de ROOM
            items(productList, key = { it.id }) { producto ->
                Card(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = producto.nombre, fontWeight = FontWeight.Bold)
                            Text(text = String.format("$%.2f", producto.precio)) // Formateo para consistencia
                        }
                        // 🟢 LLAMADA A ROOM: elimina de la base de datos
                        IconButton(onClick = { calculatorVM.removeProduct(producto) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                        }
                    }
                }
            }
        }

        val totalFormteado = String.format("Total: $%.2f", total)
        Text(
            text = totalFormteado,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}


*/


/*package com.mexiti.cronoapp.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mexiti.cronoapp.ui.components.MainTopBar
import com.mexiti.cronoapp.viewmodel.CalculatorViewModel
import com.mexiti.cronoapp.viewmodel.ConcertViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorView(
    navController: NavController,
    dataVM: ConcertViewModel
) {
    Scaffold(
        topBar = {
            MainTopBar(title = "Calculadora de compras en concierto") {
                navController.popBackStack()
            }
        }
    ) { innerPadding ->
        ContentCalculatorView(modifier = Modifier.padding(innerPadding), navController = navController)
    }
}

@Composable
fun ContentCalculatorView(modifier: Modifier, navController: NavController, calculatorVM: CalculatorViewModel = viewModel()) {
    val uiState by calculatorVM.uiState.collectAsState()
    val total = uiState.productList.sumOf { it.precio }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = uiState.newProductName,
            onValueChange = { calculatorVM.onNameChange(it) },
            label = { Text("Nombre del producto") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = uiState.newProductPrice,
            onValueChange = { calculatorVM.onPriceChange(it) },
            label = { Text("Precio") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { calculatorVM.addProduct() },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Agregar")
        }

        uiState.errorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f) // Para que ocupe el espacio disponible
        ) {
            items(uiState.productList) { producto ->
                Card(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = producto.nombre, fontWeight = FontWeight.Bold)
                            Text(text = "$${producto.precio}")
                        }
                        IconButton(onClick = { calculatorVM.removeProduct(producto.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                        }
                    }
                }
            }
        }

        val totalFormteado = String.format("Total: $%.2f", total)
        Text(
            text = totalFormteado,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

    }
}

 */
