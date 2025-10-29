package com.mexiti.cronoapp.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mexiti.cronoapp.R

@Composable
fun FloatButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.onPrimaryContainer, // Azul profundo
        contentColor = Color.White,
        modifier = Modifier.shadow(8.dp, shape = CircleShape)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.calculate),
            contentDescription = "Agregar"
        )
    }
}

@Composable
fun FloatButtonMinus(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
        contentColor = Color.White,
        modifier = Modifier.shadow(8.dp, shape = CircleShape)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.account),
            contentDescription = "Agregar"
        )
    }
}

@Composable
fun MainIconButton(icon: ImageVector, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )
    }
}

@Composable
fun CircleButton(
    icon: Painter,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        contentPadding = PaddingValues(12.dp),
        enabled = enabled,
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .shadow(4.dp, shape = CircleShape),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = Color(0xFFEB0909), // Azul medio
            contentColor = Color.White,
            disabledContainerColor = Color(0xFF000000),
            disabledContentColor = Color.White
        )
    ) {
        Icon(
            painter = icon,
            contentDescription = "",
            modifier = Modifier.size(28.dp)
        )
    }
}

