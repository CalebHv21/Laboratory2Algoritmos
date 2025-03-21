package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NQueenProblemTest {

    @Test
    void testNQueenProblem() {
        System.out.println("N QUEEN PROBLEM - TEST 4x4");
        testBoard(4);

        System.out.println("\nN QUEEN PROBLEM - TEST 8x8");
        testBoard(8);
    }

    /**
     * Método auxiliar para probar un tablero de tamaño específico
     */
    private void testBoard(int size) {
        NQueenProblem problem = new NQueenProblem(size);

        // Resolver el problema
        boolean solved = problem.solve();

        // Verificar que se haya resuelto
        assertTrue(solved, "El problema de " + size + "x" + size + " debería tener solución");

        // Obtener y mostrar el tablero
        int[][] board = problem.getBoard();
        System.out.println("Tablero " + size + "x" + size + ":");
        printBoard(board);

        // Verificar que la solución es válida
        assertTrue(isValidSolution(board), "La solución para " + size + "x" + size + " debe ser válida");
    }

    /**
     * Verifica que un tablero resuelto sea válido
     */
    private boolean isValidSolution(int[][] board) {
        int n = board.length;

        // Verificar que haya exactamente una reina en cada fila
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = 0; j < n; j++) {
                sum += board[i][j];
            }
            if (sum != 1) {
                return false;
            }
        }

        // Verificar que haya exactamente una reina en cada columna
        for (int j = 0; j < n; j++) {
            int sum = 0;
            for (int i = 0; i < n; i++) {
                sum += board[i][j];
            }
            if (sum != 1) {
                return false;
            }
        }

        // Verificar diagonales
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 1) {
                    // Verificar diagonal superior izquierda
                    for (int k = 1; i - k >= 0 && j - k >= 0; k++) {
                        if (board[i - k][j - k] == 1) {
                            return false;
                        }
                    }

                    // Verificar diagonal superior derecha
                    for (int k = 1; i - k >= 0 && j + k < n; k++) {
                        if (board[i - k][j + k] == 1) {
                            return false;
                        }
                    }

                    // Verificar diagonal inferior izquierda
                    for (int k = 1; i + k < n && j - k >= 0; k++) {
                        if (board[i + k][j - k] == 1) {
                            return false;
                        }
                    }

                    // Verificar diagonal inferior derecha
                    for (int k = 1; i + k < n && j + k < n; k++) {
                        if (board[i + k][j + k] == 1) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * Imprime un tablero de N reinas
     */
    private void printBoard(int[][] board) {
        int n = board.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}