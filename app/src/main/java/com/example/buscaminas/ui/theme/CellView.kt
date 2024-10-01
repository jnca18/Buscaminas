package com.example.buscaminas.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.buscaminas.model.Cell

/**
 * Composable que representa una celda del tablero en el juego de Buscaminas.
 *
 * @param cell Instancia de la celda (Cell) que contiene su estado actual.
 * @param onClick Función que se ejecuta al hacer clic en la celda.
 */
@Composable
fun CellView(cell: Cell, onClick: () -> Unit) {
    val backgroundColor = when {
        cell.isRevealed -> Color.Cyan
        cell.isFlagged -> Color.Red
        else -> Color.LightGray
    }
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(backgroundColor, shape = CircleShape)
            .clickable { onClick() },  // Llama a la función onClick cuando se toca la celda
        contentAlignment = Alignment.Center
    ) {
        if (cell.isRevealed && cell.isMine) {
            Text("💣")
        } else if (cell.isRevealed) {
            Text(cell.neighboringMines.toString())
        }
    }
}
