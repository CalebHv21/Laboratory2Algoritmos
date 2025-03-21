package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SudokuSolverTest {

    @Test
    void testSudokuSolver() {
        // Crear instancia del solucionador
        SudokuSolver solver = new SudokuSolver();

        // Probar 5 tableros diferentes
        System.out.println("SUDOKU PROBLEM - TEST 1");
        testBoard(solver, createBoard1(), "Tablero 1");

        System.out.println("\nSUDOKU PROBLEM - TEST 2");
        testBoard(solver, createBoard2(), "Tablero 2");

        System.out.println("\nSUDOKU PROBLEM - TEST 3");
        testBoard(solver, createBoard3(), "Tablero 3");

        System.out.println("\nSUDOKU PROBLEM - TEST 4");
        testBoard(solver, createBoard4(), "Tablero 4");

        System.out.println("\nSUDOKU PROBLEM - TEST 5");
        testBoard(solver, createBoard5(), "Tablero 5");

        // Probar un tablero generado aleatoriamente
        System.out.println("\nSUDOKU PROBLEM - RANDOM BOARD");
        int[][] randomBoard = solver.generateRandomBoard(40);
        testBoard(solver, randomBoard, "Tablero Aleatorio");
    }

    /**
     * Método auxiliar para probar un tablero específico
     */
    private void testBoard(SudokuSolver solver, int[][] board, String boardName) {
        System.out.println("Tablero inicial " + boardName + ":");
        printBoard(board);

        // Hacer una copia del tablero para resolverlo
        int[][] solution = solver.copyBoard(board);

        // Resolver el tablero
        boolean solved = solver.solveBoard(solution);

        // Verificar que se haya resuelto
        assertTrue(solved, "El tablero " + boardName + " debería tener solución");

        // Imprimir la solución
        System.out.println("Solución " + boardName + ":");
        printBoard(solution);

        // Verificar que la solución es válida
        assertTrue(isValidSolution(solver, solution), "La solución para " + boardName + " debe ser válida");
    }

    /**
     * Verifica que un tablero resuelto sea válido
     */
    private boolean isValidSolution(SudokuSolver solver, int[][] board) {
        // Verificar que no hay ceros
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    return false;
                }

                // Guardar el valor actual
                int current = board[row][col];

                // Temporalmente poner a cero para verificar validez
                board[row][col] = 0;

                // Verificar si el número es válido en esta posición
                if (!solver.isValidPlacement(board, current, row, col)) {
                    // Restaurar el valor
                    board[row][col] = current;
                    return false;
                }

                // Restaurar el valor
                board[row][col] = current;
            }
        }

        return true;
    }

    /**
     * Imprime un tablero de Sudoku en la consola
     */
    private void printBoard(int[][] board) {
        for (int row = 0; row < 9; row++) {
            if (row % 3 == 0 && row != 0) {
                System.out.println("---------------------");
            }

            for (int col = 0; col < 9; col++) {
                if (col % 3 == 0 && col != 0) {
                    System.out.print("| ");
                }

                if (board[row][col] == 0) {
                    System.out.print("_ ");
                } else {
                    System.out.print(board[row][col] + " ");
                }
            }

            System.out.println();
        }
    }

    // Crear tableros predefinidos
    private int[][] createBoard1() {
        return new int[][] {
                {0, 8, 0, 0, 0, 0, 0, 0, 9},
                {2, 0, 0, 0, 0, 0, 0, 8, 0},
                {4, 3, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 8, 0, 0, 0, 0, 0, 0},
                {6, 2, 1, 0, 8, 0, 9, 0, 4},
                {0, 9, 0, 2, 4, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
    }

    private int[][] createBoard2() {
        return new int[][] {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
    }

    private int[][] createBoard3() {
        return new int[][] {
                {0, 0, 0, 2, 6, 0, 7, 0, 1},
                {6, 8, 0, 0, 7, 0, 0, 9, 0},
                {1, 9, 0, 0, 0, 4, 5, 0, 0},
                {8, 2, 0, 1, 0, 0, 0, 4, 0},
                {0, 0, 4, 6, 0, 2, 9, 0, 0},
                {0, 5, 0, 0, 0, 3, 0, 2, 8},
                {0, 0, 9, 3, 0, 0, 0, 7, 4},
                {0, 4, 0, 0, 5, 0, 0, 3, 6},
                {7, 0, 3, 0, 1, 8, 0, 0, 0}
        };
    }

    private int[][] createBoard4() {
        return new int[][] {
                {0, 2, 0, 6, 0, 8, 0, 0, 0},
                {5, 8, 0, 0, 0, 9, 7, 0, 0},
                {0, 0, 0, 0, 4, 0, 0, 0, 0},
                {3, 7, 0, 0, 0, 0, 5, 0, 0},
                {6, 0, 0, 0, 0, 0, 0, 0, 4},
                {0, 0, 8, 0, 0, 0, 0, 1, 3},
                {0, 0, 0, 0, 2, 0, 0, 0, 0},
                {0, 0, 9, 8, 0, 0, 0, 3, 6},
                {0, 0, 0, 3, 0, 6, 0, 9, 0}
        };
    }

    private int[][] createBoard5() {
        return new int[][] {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 3, 0, 8, 5},
                {0, 0, 1, 0, 2, 0, 0, 0, 0},
                {0, 0, 0, 5, 0, 7, 0, 0, 0},
                {0, 0, 4, 0, 0, 0, 1, 0, 0},
                {0, 9, 0, 0, 0, 0, 0, 0, 0},
                {5, 0, 0, 0, 0, 0, 0, 7, 3},
                {0, 0, 2, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 4, 0, 0, 0, 9}
        };
    }
}