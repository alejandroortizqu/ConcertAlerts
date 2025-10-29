package com.mexiti.cronoapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mexiti.cronoapp.R

/* ===========================
   Common UI Components
   =========================== */

@Composable
fun formatTiempo(time: Long): String {
    val segundos = time % 60
    val minutos = (time / 60) % 60
    val horas = time / 3600
    return String.format("%02d:%02d:%02d", horas, minutos, segundos)
}

/* ---------- Título principal ---------- */
@Composable
fun MainTitle(title: String) {
    Text(
        text = title,
        color = MaterialTheme.colorScheme.onPrimary,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

/* ---------- Campo de texto ---------- */
@Composable
fun MainTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp
            )
        },
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onBackground
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 6.dp)
    )
}

/* ---------- Tarjeta de Cronómetro ---------- */
@Composable
fun CronCard(
    title: String,
    crono: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.fps),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = crono,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(top = 6.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

/* ===========================
   Previews
   =========================== */
@Preview(showBackground = true, backgroundColor = 0xFF001F3F)
@Composable
fun MainTitlePreview() {
    MainTitle(title = "CronoApp")
}

@Preview(showBackground = true)
@Composable
fun MainTextFieldPreview() {
    MainTextField(value = "00:05:00", onValueChange = {}, label = "Score")
}

@Preview(showBackground = true)
@Composable
fun CronCardPreview() {
    CronCard(title = "Entrenamiento", crono = "01:24:00") {}
}
