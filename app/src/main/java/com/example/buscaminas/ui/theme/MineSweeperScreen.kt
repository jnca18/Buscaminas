package com.example.buscaminas.ui.theme

// MineSweeperScreen.kt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.example.buscaminas.logic.MineSweeperGame

/**
 * Composable que representa la pantalla principal del juego Buscaminas.
 * @param game Instancia del juego de Buscaminas que contiene el estado y la lógica del juego.
 */
@Composable
fun MineSweeperScreen(game: MineSweeperGame) {
    val cells = game.getCells() // Obtiene todas las celdas
    Column {
        for (row in cells) {
            Row {
                for (cell in row) {
                    CellView(cell) {
                        game.revealCell(cell.row, cell.col)  // Lógica para revelar la celda
                    }
                }
            }
        }
    }
}
