package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RatInMazeTest {

    @Test
    void printSolution() {

        RatInMaze rat = new RatInMaze();
        int[][] maze = {
                {1, 0, 0, 0},
                {1, 1, 0, 1},
                {0, 1, 0, 0},
                {1, 1, 1, 1},
        };
        System.out.println("\nRat in a maze for a 4x4 board");
        System.out.println(printBoard(maze));
        System.out.println("\nSolution");
        System.out.println(rat.printSolution(maze));
    }//End printSolution

    private String printBoard(int[][] maze) {

       String result = "";
       int n = maze.length;
       for (int i = 0; i < n; i++){
           for (int j = 0; j < n; j++){
               //Operadores ternarios true (?) o false (:)
               //Si choca con pared pone 0, si hay solución coloca " " + maze[i][j]
               result += maze[i][j] == 0 ? " 0" : " " + maze[i][j]; //Si maze[i][j] es cero entonces " ", sino entonces " " + maze[i][j] //Operadores ternarios, como if-else --> ? if, : else
           }
        result += "\n"; //salto a la siguiente fila
       }

       return result;
    }


}//END TEST