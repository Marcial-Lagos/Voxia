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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay

// ==========================================
// 1. MODELO (Model)
// ==========================================
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

// ==========================================
// 2. VIEWMODEL (El cerebro y la lógica)
// ==========================================
class GameViewModel : ViewModel() {
    var currentIndex by mutableIntStateOf(0)
        private set
    var score by mutableIntStateOf(0)
        private set
    var isGameOver by mutableStateOf(false)
        private set
    var showFeedback by mutableStateOf(false)
        private set
    var isAnswerCorrect by mutableStateOf(false)
        private set

    val currentCard: Flashcard
        get() = gameLevels[currentIndex]

    fun checkAnswer(userSelectedTrue: Boolean) {
        isAnswerCorrect = (currentCard.isTranslationCorrect == userSelectedTrue)
        if (isAnswerCorrect) score++
        showFeedback = true
    }

    fun advanceLevel() {
        showFeedback = false
        if (currentIndex < gameLevels.size - 1) {
            currentIndex++
        } else {
            isGameOver = true
        }
    }

    fun resetGame() {
        currentIndex = 0
        score = 0
        isGameOver = false
        showFeedback = false
    }
}

// ==========================================
// 3. VISTA (View)
// ==========================================
@Composable
fun HomeScreen(viewModel: GameViewModel = viewModel()) {

    LaunchedEffect(viewModel.showFeedback) {
        if (viewModel.showFeedback) {
            delay(1000)
            viewModel.advanceLevel()
        }
    }

    if (viewModel.isGameOver) {
        GameOverScreen(score = viewModel.score) {
            viewModel.resetGame()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Nivel ${viewModel.currentIndex + 1} de ${gameLevels.size}", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(32.dp))

            Text(text = viewModel.currentCard.turkishWord, fontSize = 48.sp, fontWeight = FontWeight.Bold)
            Text(text = "¿Significa...", fontSize = 20.sp, modifier = Modifier.padding(top = 16.dp))
            Text(text = "\"${viewModel.currentCard.proposedSpanish}\"?", fontSize = 32.sp, color = MaterialTheme.colorScheme.primary)

            Spacer(modifier = Modifier.height(32.dp))

            if (viewModel.showFeedback) {
                Text(
                    text = if (viewModel.isAnswerCorrect) "¡Correcto! 🎉" else "¡Incorrecto! ❌",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (viewModel.isAnswerCorrect) Color(0xFF4CAF50) else MaterialTheme.colorScheme.error
                )
            } else {
                Spacer(modifier = Modifier.height(32.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { viewModel.checkAnswer(true) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    enabled = !viewModel.showFeedback
                ) {
                    Text("Verdadero", fontSize = 18.sp)
                }

                Button(
                    onClick = { viewModel.checkAnswer(false) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    enabled = !viewModel.showFeedback
                ) {
                    Text("Falso", fontSize = 18.sp)
                }
            }
        }
    }
}

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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}