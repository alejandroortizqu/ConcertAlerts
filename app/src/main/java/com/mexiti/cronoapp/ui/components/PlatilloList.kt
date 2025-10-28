package com.mexiti.cronoapp.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mexiti.cronoapp.model.Platillo

@Composable
fun PlatilloItem(platillo: Platillo, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(platillo.drawableResourceId),
                contentDescription = stringResource(platillo.stringResourceId),
                modifier = Modifier
                    .size(96.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
            Column(Modifier.padding(8.dp)) {
                Text(
                    text = stringResource(platillo.stringResourceId),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(text = "Ubicación: ${platillo.ubicacion}")
                Text(text = "Fecha: ${platillo.fecha}")
            }
        }
    }
}

@Composable
fun PlatilloList(platillos: List<Platillo>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(platillos) { PlatilloItem(it) }
    }
}
