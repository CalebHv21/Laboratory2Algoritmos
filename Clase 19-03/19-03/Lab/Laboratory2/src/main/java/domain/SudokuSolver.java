package domain;

public class SudokuSolver {
    private static final int GRID_SIZE = 9;

    /**
     * Resuelve un tablero de Sudoku utilizando el algoritmo de backtracking
     * @param board el tablero de Sudoku a resolver (0 representa celdas vacías)
     * @return true si se encontró una solución, false en caso contrario
     */
    public boolean solveBoard(int[][] board) {
        // Buscar una celda vacía en el tablero
        int[] emptyCell = findEmptyCell(board);
        int row = emptyCell[0];
        int col = emptyCell[1];

        // Si no se encuentra ninguna celda vacía, el tablero está resuelto
        if (row == -1) {
            return true;
        }

        // Intentar colocar dígitos del 1 al 9 en la celda vacía
        for (int num = 1; num <= 9; num++) {
            // Verificar si la colocación de este número es válida
            if (isValidPlacement(board, num, row, col)) {
                // Colocar el número
                board[row][col] = num;

                // Intentar resolver recursivamente el resto del tablero
                if (solveBoard(board)) { //Le pasa a solveBoard el nuevo tablero hasta que se llene
                    return true; // Solución encontrada
                }

                // Si colocar el número no llevó a una solución, retroceder
                board[row][col] = 0; // Resetear la celda
            }
        }

        // No se encontró solución con la configuración actual
        return false;
    }

    /**
     * Encuentra la primera celda vacía en el tablero
     * @param board el tablero de Sudoku
     * @return array con [fila, columna] de la celda vacía, o [-1, -1] si no se encuentra ninguna
     */
    private int[] findEmptyCell(int[][] board) {
        int[] cell = {-1, -1}; // Valor por defecto si no hay celdas vacías

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (board[row][col] == 0) {
                    cell[0] = row;
                    cell[1] = col;
                    return cell;
                }
            }
        }

        return cell; // No se encontró ninguna celda vacía
    }

    /**
     * Verifica si un número puede ser colocado en una posición específica
     * @param board el tablero de Sudoku
     * @param number el número a colocar
     * @param row el índice de la fila
     * @param col el índice de la columna
     * @return true si la colocación es válida, false en caso contrario
     */
    public boolean isValidPlacement(int[][] board, int number, int row, int col) {
        // Verificar fila
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[row][i] == number) { // Recorre la fila, columna por columna, buscando si hay coincidencias con el número
                return false;
            }
        }

        // Verificar columna
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[i][col] == number) { // Recorre la columna, fila por fila, buscando si hay coincidencias con el número
                return false;
            }
        }

        // Verificar caja 3x3
        int boxStartRow = row - (row % 3);
        int boxStartCol = col - (col % 3);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[boxStartRow + r][boxStartCol + c] == number) {
                    return false;
                }
            }
        }

        // Si pasó todas las verificaciones, la colocación es válida
        return true;
    }

    /**
     * Crea una copia profunda de un tablero
     * @param board el tablero a copiar
     * @return una nueva instancia con los mismos datos
     */
    public int[][] copyBoard(int[][] board) {
        int[][] copy = new int[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, GRID_SIZE);
        }
        return copy;
    }

    /**
     * Genera un tablero aleatorio con números válidos
     * @param emptyCells número de celdas que deben estar vacías
     * @return tablero generado aleatoriamente
     */
    public int[][] generateRandomBoard(int emptyCells) {
        int[][] board = new int[GRID_SIZE][GRID_SIZE];

        // Llenar las cajas diagonales primero (pueden llenarse independientemente)
        fillDiagonalBoxes(board);

        // Resolver el resto del tablero
        solveBoard(board);

        // Crear una copia del tablero resuelto
        int[][] boardWithBlanks = copyBoard(board);

        // Eliminar números para crear el puzzle
        makeBlanks(boardWithBlanks, emptyCells);

        return boardWithBlanks;
    }

    /**
     * Llena las cajas diagonales 3x3 con números aleatorios válidos
     */
    private void fillDiagonalBoxes(int[][] board) {
        for (int box = 0; box < 3; box++) {
            fillBox(board, box * 3, box * 3);
        }
    }

    /**
     * Llena una caja 3x3 con números aleatorios válidos
     */
    private void fillBox(int[][] board, int startRow, int startCol) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        shuffleArray(nums);

        int numIndex = 0;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[startRow + r][startCol + c] = nums[numIndex++];
            }
        }
    }

    /**
     * Mezcla un array utilizando el algoritmo Fisher-Yates
     */
    private void shuffleArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = (int)(Math.random() * (i + 1));
            int temp = array[i];
            array[i] = array[index];
            array[index] = temp;
        }
    }

    /**
     * Elimina números del tablero para crear el puzzle
     */
    private void makeBlanks(int[][] board, int count) {
        int removed = 0;
        while (removed < count) {
            int row = (int)(Math.random() * GRID_SIZE);
            int col = (int)(Math.random() * GRID_SIZE);

            if (board[row][col] != 0) {
                board[row][col] = 0;
                removed++;
            }
        }
    }
}