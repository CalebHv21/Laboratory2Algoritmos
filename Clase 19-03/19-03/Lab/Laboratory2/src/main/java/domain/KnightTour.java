package domain;

import java.util.Arrays;

public class KnightTour {

    private int N = 8; //Extensión de row y col del tablero

    private int[][] sol = new int[N][N]; //Array que guardará los movimientos del caballo (matriz de 8*8)

    // Verifica si el movimiento es válido
    public boolean isValid(int x, int y) {
        //Retorna true si se cumplen las condiciones
        return (x >= 0 && x < N && y >= 0 && y < N && sol[x][y] == -1); // El movimiento debe estar dentro del tablero y que la casilla esté vacía (-1)
    } //End isValid

    // Muestra la solución
    public String printSolution() {

        StringBuilder result = new StringBuilder(); //StringBuilder para permitir usar format

        for (int x = 0; x < N; x++) { //Recorre filas de sol
            for (int y = 0; y < N; y++) //Recorre columnas de sol
                result.append(String.format("%3d ", sol[x][y]));  // String.format para darle formato

            result.append("\n");  // Salto de línea después de cada fila
        }

        return result.toString(); //Retorna el toString

    } //End printSolution()

    //Hace el backtracking
    public boolean knightTour(int x, int y, int move, int[] xMove, int[] yMove) {

        int xNext, yNext; //Almacenarán coordenadas por las que puede moverse el caballo

        //Cuando el tablero se llene con los 64 movimientos retornará true
        if (move == N*N)
            return true;

        //Recorre las 8 posibles jugadas del caballo
        for (int k = 0; k < 8; k++) {
            xNext = x + xMove[k]; //Almacena en xNext x + el posible movimiento de x en el índice k
            yNext = y + yMove[k]; //En yNext guarda y + el desplazamiento en vertical en el índice k

           //Llama isValid para verificar que la casilla en la posición (xNext, yNext) esté vacía
            if (isValid(xNext, yNext)) {
                sol[xNext][yNext] = move; //De ser válido, se asigna el número de movimiento actual a las coordenadas (xNext, yNext) de sol

                //Llamada recursiva el método pero aumentando un movimiento, y así hasta que el número de movimientos sea igual a N*N (8*8)
                if (knightTour(xNext, yNext, move+1, xMove, yMove))
                    return true; //Retorna true al encontrar solución
                else

                    // backtracking
                    sol[xNext][yNext] = -1; //Se desmarca la casilla visitada en caso de que no se encuentre una solución

            }//end if
        }//end for

        return false; //False en caso de no hallar solución

    } //End knightTour

    public boolean solveKnightTour() {
        //Coloca el valor de -1 en cada casilla de sol para establecerlas como vacías
        for (int[] row : sol)
            Arrays.fill(row, -1);

        //Posibles movimientos del caballo en cada dirección:
        int[] xMove = {2, 1, -1, -2, -2, -1, 1, 2};
        int[] yMove = {1, 2, 2, 1, -1, -2, -2, -1};

        //El caballo comienza por la primera casilla (0,0)
        sol[0][0] = 0; //Movimiento 0

        //Si no se encuentra una solución con knightTour retorna false
        if (!knightTour(0, 0, 1, xMove, yMove)) {
           // System.out.println("Solution does not exist");
            return false;
        } else
            printSolution(); //Si encuentra solución imprime la solución

        return true;
    } //End findKnightTourSol()

}//END CLASS
