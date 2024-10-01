package com.example.buscaminas.logic

import com.example.buscaminas.model.MineField

/**
 * Clase que gestiona la lógica principal del juego de Buscaminas.
 *
 * @property rows Número de filas en el tablero.
 * @property cols Número de columnas en el tablero.
 * @property mines Número total de minas distribuidas en el tablero.
 */
class MineSweeperGame(private val rows: Int, private val cols: Int, private val mines: Int) {

    // El tablero del juego representado por una instancia de MineField
    val mineField = MineField(rows, cols, mines)

    // Variable que indica si el juego ha terminado (true si el juego ha terminado, false si aún continúa)
    var isGameOver = false
        private set  // Solo se puede modificar internamente, pero se puede leer externamente

    /**
     * Revela una celda en el tablero.
     *
     * Si la celda revelada contiene una mina, el juego termina. Si no contiene una mina,
     * se verifica si el jugador ha ganado el juego.
     *
     * @param row Índice de la fila de la celda a revelar.
     * @param col Índice de la columna de la celda a revelar.
     */
    fun revealCell(row: Int, col: Int) {
        // Si el juego no ha terminado y la celda se puede revelar sin encontrar una mina
        if (!isGameOver && mineField.revealCell(row, col)) {
            checkWinCondition() // Verifica si todas las celdas sin minas han sido reveladas
        } else {
            isGameOver = true  // El juego termina si se revela una mina
            // Se puede invocar una lógica adicional para mostrar un mensaje de derrota
        }
    }

    /**
     * Verifica si el jugador ha ganado.
     *
     * El jugador gana cuando todas las celdas no minadas están reveladas.
     * Si esto ocurre, la función establece el estado de `isGameOver` a true.
     */
    private fun checkWinCondition() {
        // Verifica si todas las celdas son minas o están reveladas
        if (mineField.cells.all { row -> row.all { it.isMine || it.isRevealed } }) {
            isGameOver = true
            // Se puede invocar lógica adicional para mostrar un mensaje de victoria
        }
    }

    /**
     * Reinicia el juego.
     *
     * Esta función reinicia el estado del juego a "en progreso" y genera un nuevo tablero
     * con minas y valores calculados para cada celda.
     */
    fun resetGame() {
        isGameOver = false  // Reinicia el estado del juego
        mineField.generateMines()  // Genera nuevas minas en posiciones aleatorias
        mineField.calculateNeighboringMines()  // Calcula las minas vecinas para cada celda
    }

    /**
     * Devuelve todas las celdas del tablero actual.
     *
     * @return Una lista bidimensional que representa las celdas del tablero.
     */
    fun getCells() = mineField.cells
}