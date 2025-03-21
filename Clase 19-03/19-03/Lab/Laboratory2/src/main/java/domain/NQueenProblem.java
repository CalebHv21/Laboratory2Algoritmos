package domain;

public class NQueenProblem {
    private int size; // Tamaño del tablero (NxN)
    private int[] solution; // Array que almacena la solución (posición de cada reina)

    /**
     * Constructor para el problema de las N-reinas
     * @param n Tamaño del tablero
     */
    public NQueenProblem(int n) {
        this.size = n;
        this.solution = new int[n];
        // Inicializar el array de solución con -1 (sin reina colocada)
        for (int i = 0; i < n; i++) {
            solution[i] = -1;
        }
    }

    /**
     * Resuelve el problema de las N reinas
     * @return true si se encontró una solución, false en caso contrario
     */
    public boolean solve() {
        return backtrack(0);
    }

    /**
     * Implementación del algoritmo de backtracking
     * @param row Fila actual a procesar
     * @return true si se encontró una solución, false en caso contrario
     */
    private boolean backtrack(int row) {
        // Si hemos procesado todas las filas, hemos encontrado una solución
        if (row >= size) {
            return true;
        }

        // Intentar colocar una reina en cada columna de la fila actual
        for (int col = 0; col < size; col++) {
            // Verificar si es seguro colocar una reina en esta posición
            if (isSafe(row, col)) {
                // Colocar la reina
                solution[row] = col;

                // Recursivamente intentar colocar el resto de reinas
                if (backtrack(row + 1)) {
                    return true; // Si encontramos una solución, terminamos
                }

                // Si colocar la reina no lleva a una solución, retrocedemos
                solution[row] = -1; // Quitar la reina (backtrack)
            }
        }

        // Si no se puede colocar una reina en ninguna columna de esta fila
        return false;
    }

    /**
     * Verifica si es seguro colocar una reina en la posición (row, col)
     * @param row Fila
     * @param col Columna
     * @return true si es seguro, false en caso contrario
     */
    private boolean isSafe(int row, int col) {
        // Verificar por filas (solo necesitamos verificar filas anteriores)
        for (int i = 0; i < row; i++) {
            // Si hay una reina en la misma columna
            if (solution[i] == col) {
                return false;
            }

            // Si hay una reina en la diagonal
            if (Math.abs(solution[i] - col) == Math.abs(i - row)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Obtiene la representación del tablero con las reinas colocadas
     * @return Matriz de 0s y 1s (1 indica la presencia de una reina)
     */
    public int[][] getBoard() {
        int[][] board = new int[size][size];

        for (int i = 0; i < size; i++) {
            if (solution[i] != -1) {
                board[i][solution[i]] = 1;
            }
        }

        return board;
    }

    /**
     * Obtiene la solución (posición de cada reina)
     * @return Array con las posiciones de las reinas
     */
    public int[] getSolution() {
        return solution;
    }
}