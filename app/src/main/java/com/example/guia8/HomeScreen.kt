package com.example.guia8

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay // <-- Importante para hacer la pausa de 1 segundo

// 1. Estructura de Datos
data class Flashcard(
    val turkishWord: String,
    val proposedSpanish: String,
    val isTranslationCorrect: Boolean
)

val gameLevels = listOf(
    Flashcard("Merhaba", "Hola", true),
    Flashcard("Teşekkürler", "Adiós", false),
    Flashcard("Lütfen", "Por favor", true),
    Flashcard("Evet", "No", false),
    Flashcard("Hayır", "No", true),
    Flashcard("Su", "Agua", true),
    Flashcard("Ekmek", "Queso", false)
)

// 2. Pantalla Principal
@Composable
fun HomeScreen() {
    var currentIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var isGameOver by remember { mutableStateOf(false) }

    // Nuevas variables para controlar el mensaje de "Correcto/Incorrecto"
    var showFeedback by remember { mutableStateOf(false) }
    var isAnswerCorrect by remember { mutableStateOf(false) }

    // Este bloque hace la pausa: Si showFeedback es true, espera 1 segundo y avanza
    LaunchedEffect(showFeedback) {
        if (showFeedback) {
            delay(1000) // Espera 1000 milisegundos (1 segundo)
            showFeedback = false // Oculta el mensaje

            // Avanza de nivel o termina el juego
            if (currentIndex < gameLevels.size - 1) {
                currentIndex++
            } else {
                isGameOver = true
            }
        }
    }

    if (isGameOver) {
        GameOverScreen(score = score) {
            currentIndex = 0
            score = 0
            isGameOver = false
            showFeedback = false
        }
    } else {
        val currentCard = gameLevels[currentIndex]

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Nivel ${currentIndex + 1} de ${gameLevels.size}", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(32.dp))

            Text(text = currentCard.turkishWord, fontSize = 48.sp, fontWeight = FontWeight.Bold)
            Text(text = "¿Significa...", fontSize = 20.sp, modifier = Modifier.padding(top = 16.dp))
            Text(text = "\"${currentCard.proposedSpanish}\"?", fontSize = 32.sp, color = MaterialTheme.colorScheme.primary)

            Spacer(modifier = Modifier.height(32.dp))

            // Aquí mostramos el mensaje de Correcto o Incorrecto
            if (showFeedback) {
                Text(
                    text = if (isAnswerCorrect) "¡Correcto! 🎉" else "¡Incorrecto! ❌",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isAnswerCorrect) Color(0xFF4CAF50) else MaterialTheme.colorScheme.error
                )
            } else {
                // Espacio vacío para que los botones no salten hacia arriba y abajo
                Spacer(modifier = Modifier.height(32.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Botón VERDADERO
                Button(
                    onClick = {
                        isAnswerCorrect = (currentCard.isTranslationCorrect == true)
                        if (isAnswerCorrect) score++
                        showFeedback = true // Muestra el mensaje y activa la pausa
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    enabled = !showFeedback // Desactiva el botón mientras sale el mensaje
                ) {
                    Text("Verdadero", fontSize = 18.sp)
                }

                // Botón FALSO
                Button(
                    onClick = {
                        isAnswerCorrect = (currentCard.isTranslationCorrect == false)
                        if (isAnswerCorrect) score++
                        showFeedback = true // Muestra el mensaje y activa la pausa
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    enabled = !showFeedback // Desactiva el botón mientras sale el mensaje
                ) {
                    Text("Falso", fontSize = 18.sp)
                }
            }
        }
    }
}

// 3. Pantalla de Resultados
@Composable
fun GameOverScreen(score: Int, onRestart: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "¡Juego Terminado!", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Puntuación: $score / ${gameLevels.size}", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onRestart) {
            Text("Jugar de nuevo")
        }
    }
}

// 4. Vista Previa
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}