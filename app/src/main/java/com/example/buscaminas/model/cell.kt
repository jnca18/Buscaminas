package com.example.buscaminas.model

/**
 * Clase de datos que representa una celda individual en el tablero de Buscaminas.
 *
 * @property row La fila en la que se encuentra la celda dentro del tablero.
 * @property col La columna en la que se encuentra la celda dentro del tablero.
 * @property isMine Indica si la celda contiene una mina (true si contiene, false si no).
 * @property isRevealed Indica si la celda ha sido revelada (true si ya fue revelada, false si está oculta).
 * @property isFlagged Indica si el jugador ha marcado la celda con una bandera, indicando que sospecha que contiene una mina.
 * @property neighboringMines El número de minas presentes en las celdas adyacentes (valor entre 0 y 8).
 */
data class Cell(
    val row: Int,                  // Fila en la que se encuentra la celda
    val col: Int,                  // Columna en la que se encuentra la celda
    var isMine: Boolean = false,   // Indica si la celda tiene una mina
    var isRevealed: Boolean = false, // Indica si la celda ha sido revelada
    var isFlagged: Boolean = false,  // Indica si la celda ha sido marcada con una bandera
    var neighboringMines: Int = 0  // Número de minas vecinas a la celda
)