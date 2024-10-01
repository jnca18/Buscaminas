package com.example.buscaminas.model

/**
 * Clase que representa el campo de juego (tablero) de Buscaminas.
 * @param rows Número de filas en el tablero.
 * @param cols Número de columnas en el tablero.
 * @param mines Número total de minas distribuidas en el tablero.
 */
class MineField(private val rows: Int, private val cols: Int, private val mines: Int) {

    // Matriz bidimensional que representa las celdas del tablero
    val cells: Array<Array<Cell>> = Array(rows) { row ->
        Array(cols) { col ->
            Cell(row, col)
        }
    }

    // Inicializa el tablero generando minas y calculando las minas vecinas
    init {
        generateMines()              // Coloca minas en posiciones aleatorias
        calculateNeighboringMines()  // Calcula el número de minas vecinas para cada celda
    }

    /**
     * Genera minas en el tablero en posiciones aleatorias.
     * Evita colocar minas en posiciones que ya contienen una.
     */
    internal fun generateMines() {
        var placedMines = 0
        // Coloca minas aleatoriamente hasta alcanzar el número deseado
        while (placedMines < mines) {
            val row = (0 until rows).random()  // Fila aleatoria
            val col = (0 until cols).random()  // Columna aleatoria
            // Si la celda no tiene una mina, se le asigna una y se incrementa el contador
            if (!cells[row][col].isMine) {
                cells[row][col].isMine = true
                placedMines++
            }
        }
    }

    /**
     * Calcula el número de minas vecinas para cada celda en el tablero.
     * Se ignoran las celdas que contienen minas.
     */
    internal fun calculateNeighboringMines() {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                // Solo cuenta las minas vecinas si la celda no es una mina
                if (!cells[row][col].isMine) {
                    cells[row][col].neighboringMines = countNeighboringMines(row, col)
                }
            }
        }
    }

    /**
     * Cuenta el número de minas en las celdas vecinas de una celda específica.
     * @param row Fila de la celda actual.
     * @param col Columna de la celda actual.
     * @return El número de minas vecinas.
     */
    private fun countNeighboringMines(row: Int, col: Int): Int {
        var count = 0
        // Recorre las 8 celdas vecinas (incluyendo diagonales)
        for (i in -1..1) {
            for (j in -1..1) {
                val newRow = row + i
                val newCol = col + j
                // Verifica que las coordenadas sean válidas y que la celda vecina contenga una mina
                if ((newRow in (0 until rows)) && (newCol in (0 until cols)) && cells[newRow][newCol].isMine) {
                    count++
                }
            }
        }
        return count
    }

    /**
     * Revela una celda en el tablero.
     * @param row Fila de la celda a revelar.
     * @param col Columna de la celda a revelar.
     * @return True si la celda no es una mina, False si es una mina (indica derrota).
     */
    fun revealCell(row: Int, col: Int): Boolean {
        val cell = cells[row][col]
        if (cell.isMine) {
            return false // Perdiste
        }

        cell.isRevealed = true

        // Si la celda no tiene minas vecinas, se revelan las celdas vecinas vacías
        if (cell.neighboringMines == 0) {
            revealEmptyNeighbors(row, col)
        }

        return true
    }

    /**
     * Revela recursivamente todas las celdas vecinas que no sean minas ni estén reveladas.
     * @param row Fila de la celda a partir de la cual se revelan los vecinos.
     * @param col Columna de la celda a partir de la cual se revelan los vecinos.
     */
    private fun revealEmptyNeighbors(row: Int, col: Int) {
        // Recorre las 8 celdas vecinas (incluyendo diagonales)
        for (i in -1..1) {
            for (j in -1..1) {
                val newRow = row + i
                val newCol = col + j
                // Verifica que las coordenadas sean válidas y que la celda no esté ya revelada
                if (newRow in 0 until rows && newCol in 0 until cols && !cells[newRow][newCol].isRevealed) {
                    revealCell(newRow, newCol)
                }
            }
        }
    }
}