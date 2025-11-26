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
                // icono de navegación
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
