package com.example.guia8.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.guia8.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenExpandida() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Guía 9 - Expandida") })
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(32.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Imagen de la App",
                modifier = Modifier
                    .weight(1f)
                    .height(280.dp),
                contentScale = ContentScale.Fit
            )
            Column(
                modifier = Modifier.weight(2f),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "Vista Expandida para Pantallas Grandes",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = "Esta interfaz aprovecha todo el ancho en tablets o computadores.",
                    style = MaterialTheme.typography.bodyLarge
                )
                Button(onClick = { /* Acción */ }) {
                    Text(text = "Presióname")
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 840, heightDp = 900)
@Composable
fun HomeScreenExpandidaPreview() {
    HomeScreenExpandida()
}