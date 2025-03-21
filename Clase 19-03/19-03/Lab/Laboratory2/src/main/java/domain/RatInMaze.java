package domain;

public class RatInMaze {

    /**
     * 0 Significa una pared, no hay paso
     * 1 Significa una ruta válida, sí hay paso
     * 2 Significa el camino correcto, la ruta de escape del laberinto
     */

    public String printSolution(int[][] maze) {
        String result = "";
        int n = maze.length; //Buenas prácticas
        //resolvemos iniciando en la posición fila==0, col==0
        //finalizamos en la posición fila==n-1, col==n-1

        if (solveRatInMaze(maze, 0, 0, n)) {
            for(int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    result += " " + maze[i][j];
                }
                result += "\n"; //Salto de linea a la siguiente fila
            }
        } else {
            result += "Solution does not exist";
        }

        return result;
    }

    private boolean solveRatInMaze(int[][] maze, int i, int j, int n) {
        // Si hemos llegado a la posición final, entonces ya encontramos la solución
        if(i == n-1 && j == n-1) {
            maze[i][j] = 2; //Marcamos la última posición como ruta válida
            return true;
        }

        if (isSafe(maze, i, j, n)) {
            maze[i][j] = 2; //marcamos la ruta

            // Intentar moverse en todas las direcciones posibles (derecha, abajo, izquierda, arriba)
            // Esto permite encontrar soluciones más variadas

            // Derecha
            if (solveRatInMaze(maze, i, j+1, n)) return true;

            // Abajo
            if (solveRatInMaze(maze, i+1, j, n)) return true;

            // Izquierda (opcional para soluciones más complejas)
            if (j > 0 && solveRatInMaze(maze, i, j-1, n)) return true;

            // Arriba (opcional para soluciones más complejas)
            if (i > 0 && solveRatInMaze(maze, i-1, j, n)) return true;

            //Backtrack: desmarca (i,j) como parte de la ruta seleccionada (se devuelve ya que no hay camino)
            maze[i][j] = 1; //La marcamos como posición no visitada //La desmarcamos la ruta
        }

        return false; //Si no es seguro, retorna false
    }

    private boolean isSafe(int[][] maze, int i, int j, int n) {
        return i >= 0 && i < n && j >= 0 && j < n && maze[i][j] == 1;
    }
}