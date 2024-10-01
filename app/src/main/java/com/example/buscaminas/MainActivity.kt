package com.example.buscaminas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.buscaminas.logic.MineSweeperGame
import com.example.buscaminas.model.Cell
import com.example.buscaminas.ui.theme.BuscaminasTheme

class MainActivity : ComponentActivity() {
    // Instancia de MineSweeperGame que contiene la lógica del juego
    private lateinit var game: MineSweeperGame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializa un tablero de 9x9 con 10 minas
        game = MineSweeperGame(9, 9, 10)
        // Usa Jetpack Compose para configurar el contenido de la UI
        setContent {
            BuscaminasTheme {
                // Carga la pantalla principal del juego
                MineSweeperScreen(game)
            }
        }
    }
}

/**
 * Función composable que muestra un saludo. Aquí solo es un ejemplo de cómo usar Text en Compose.
 * @param name Nombre que se muestra en el saludo.
 */
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

/**
 * Vista previa de la función Greeting para mostrar en el editor de Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BuscaminasTheme {
        Greeting("Android")
    }
}

/**
 * Pantalla principal de Buscaminas. Muestra el tablero completo con las celdas.
 * @param game Instancia de MineSweeperGame que contiene el estado del juego.
 */
@Composable
fun MineSweeperScreen(game: MineSweeperGame) {
    val cells = game.getCells() // Cambiar 'mineField.cells' a 'getCells()' si implementas el método de acceso.
    Column {
        for (row in cells) {
            Row {
                for (cell in row) {
                    CellView(cell) {
                        game.revealCell(cell.row, cell.col)  // Aquí se debería revelar la celda
                    }
                }
            }
        }
    }
}
/**
 * Composable que representa una celda individual en el tablero de Buscaminas.
 *
 * @param cell Objeto [Cell] que representa la celda del tablero.
 * @param onClick Función que se ejecuta cuando el usuario hace clic en la celda.
 */
@Composable
fun CellView(cell: Cell, onClick: () -> Unit) {
    // Determina el color de fondo de la celda según su estado
    val backgroundColor = when {
        cell.isRevealed -> Color.Cyan       // Si la celda ha sido revelada, el fondo es cyan
        cell.isFlagged -> Color.Red         // Si la celda está marcada con una bandera, el fondo es rojo
        else -> Color.LightGray              // Si no está revelada ni marcada, el fondo es gris claro
    }

    // Crea una caja que contendrá el contenido de la celda
    Box(
        modifier = Modifier
            .size(40.dp)                          // Establece el tamaño de la celda
            .background(backgroundColor, shape = CircleShape) // Aplica el color de fondo y la forma circular
            .pointerInput(Unit) {                 // Configura la detección de gestos de toque
                detectTapGestures(onTap = {
                    // Registra en la consola el toque en la celda
                    println("Cell tapped: Row ${cell.row}, Col ${cell.col}") // Log
                    onClick()                       // Llama a la función onClick para revelar la celda
                })
            },
        contentAlignment = Alignment.Center // Centra el contenido dentro de la caja
    ) {
        // Muestra el contenido de la celda según su estado
        if (cell.isRevealed && cell.isMine) {
            Text("💣")                            // Si la celda es una mina y ha sido revelada, muestra una bomba
        } else if (cell.isRevealed) {
            Text(cell.neighboringMines.toString()) // Si la celda ha sido revelada y no es una mina, muestra el número de minas vecinas
        }
    }
}

/**
 * Vista previa de la pantalla principal de Buscaminas.
 * Muestra un tablero de 9x9 con 10 minas para la vista previa en el editor.
 */
@Preview(showBackground = true)
@Composable
fun MineSweeperScreenPreview() {
    // Crea una instancia de juego para usar en la vista previa
    val game = MineSweeperGame(9, 9, 10)
    BuscaminasTheme {
        // Muestra la pantalla del juego en la vista previa
        MineSweeperScreen(game)
    }
}

