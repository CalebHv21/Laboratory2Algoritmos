package controller;

import domain.RatInMaze;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import util.Utility;

public class RatInAMazeController {
    @javafx.fxml.FXML
    private ComboBox<String> cbSelectMaze;
    @javafx.fxml.FXML
    private TableView<ObservableList<String>> tvSolutionMaze;
    @javafx.fxml.FXML
    private TableView<ObservableList<String>> tvInitialMaze;

    private RatInMaze ratInMaze;
    private int[][][] mazes; // Almacena los 5 laberintos iniciales
    private int[][][] solutions; // Almacena las soluciones de los 5 laberintos

    // Tamaños de los laberintos
    private final int[] mazeSizes = {4, 8, 10, 12, 16};

    @javafx.fxml.FXML
    public void initialize() {
        // Inicializar el solucionador de laberinto
        ratInMaze = new RatInMaze();

        // Inicializar el ComboBox con las opciones de laberinto
        ObservableList<String> mazeOptions = FXCollections.observableArrayList();
        for (int i = 1; i <= 5; i++) {
            mazeOptions.add("Maze" + i);
        }
        cbSelectMaze.setItems(mazeOptions);

        // Generar los 5 laberintos aleatorios y sus soluciones
        generateMazes();

        // Seleccionar el primer laberinto por defecto
        cbSelectMaze.getSelectionModel().select(0);
        selectedMaze(null);
    }

    /**
     * Genera los 5 laberintos aleatorios y sus soluciones
     */
    private void generateMazes() {
        mazes = new int[5][][];
        solutions = new int[5][][];

        for (int i = 0; i < 5; i++) {
            int size = mazeSizes[i];
            int[][] maze;
            int[][] solutionMaze;
            boolean hasSolution;

            // Generar laberintos hasta encontrar uno con solución
            do {
                // Generar laberinto aleatorio
                maze = generateRandomMaze(size);

                // Asegurarse de que la entrada y salida estén libres
                maze[0][0] = 1; // Entrada
                maze[size-1][size-1] = 1; // Salida

                // Crear una copia para probar si tiene solución
                solutionMaze = copyMaze(maze);

                // Verificar si el laberinto tiene solución
                ratInMaze = new RatInMaze(); // Crear nueva instancia para cada intento
                String result = ratInMaze.printSolution(solutionMaze);
                hasSolution = !result.contains("Solution does not exist");

            } while (!hasSolution);

            // Guardar el laberinto con solución encontrado
            mazes[i] = maze;
            solutions[i] = solutionMaze;
        }
    }

    /**
     * Genera un laberinto aleatorio del tamaño especificado
     */
    private int[][] generateRandomMaze(int size) {
        int[][] maze = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Generar valores aleatorios 0 (pared) o 1 (camino)
                // Usamos un 70% de probabilidad para el camino para todos los tamaños
                maze[i][j] = Utility.random(100) <= 70 ? 1 : 0;
            }
        }

        return maze;
    }

    /**
     * Crea una copia de un laberinto
     */
    private int[][] copyMaze(int[][] maze) {
        int size = maze.length;
        int[][] copy = new int[size][size];

        for (int i = 0; i < size; i++) {
            System.arraycopy(maze[i], 0, copy[i], 0, size);
        }

        return copy;
    }

    @javafx.fxml.FXML
    public void selectedMaze(ActionEvent actionEvent) {
        // Obtener el índice del laberinto seleccionado
        int mazeIndex = cbSelectMaze.getSelectionModel().getSelectedIndex();

        if (mazeIndex >= 0) {
            int size = mazeSizes[mazeIndex];

            // Crear columnas para ambas tablas
            createTableColumns(tvInitialMaze, size);
            createTableColumns(tvSolutionMaze, size);

            // Mostrar el laberinto inicial
            displayMaze(tvInitialMaze, mazes[mazeIndex]);

            // Mostrar la solución
            displayMaze(tvSolutionMaze, solutions[mazeIndex]);

            // Si no hay solución, mostrar mensaje en la consola
            if (!hasSolution(solutions[mazeIndex])) {
                System.out.println("El laberinto " + (mazeIndex + 1) + " no tiene solución");
            }
        }
    }

    /**
     * Verifica si un laberinto tiene solución
     */
    private boolean hasSolution(int[][] solutionMaze) {
        // Verificar si hay algún 2 en el laberinto (marcador de camino de solución)
        for (int i = 0; i < solutionMaze.length; i++) {
            for (int j = 0; j < solutionMaze[i].length; j++) {
                if (solutionMaze[i][j] == 2) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Crea las columnas para una tabla
     */
    private void createTableColumns(TableView<ObservableList<String>> tableView, int size) {
        // Limpiar columnas existentes
        tableView.getColumns().clear();

        // Crear columnas
        for (int i = 1; i <= size; i++) {
            final int colIndex = i - 1;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>("Col " + i);

            // Establecer ancho de columna para llenar la tabla uniformemente
            column.prefWidthProperty().bind(tableView.widthProperty().divide(size));

            // Usar cell value factory para mostrar los datos
            column.setCellValueFactory(param ->
                    new SimpleStringProperty(param.getValue().get(colIndex)));

            // Estilizar las celdas
            column.setCellFactory(col -> new TableCell<ObservableList<String>, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null || item.isEmpty()) {
                        setText("");
                        setStyle("-fx-background-color: transparent;");
                    } else {
                        if (item.equals("0")) {
                            // Pared - mostrar un cuadrado negro
                            setText("■");
                            setStyle("-fx-text-fill: #000000; -fx-font-size: 14px; -fx-font-weight: bold; -fx-alignment: center; -fx-background-color: transparent;");
                        } else if (item.equals("1")) {
                            // Camino - mostrar un espacio o un cuadrado blanco más claro
                            setText("□");
                            setStyle("-fx-text-fill: #AAAAAA; -fx-font-size: 14px; -fx-font-weight: bold; -fx-alignment: center; -fx-background-color: transparent;");
                        } else if (item.equals("2")) {
                            // Solución - mostrar un símbolo de rata o un camino destacado
                            setText("●");
                            setStyle("-fx-text-fill: #FF0000; -fx-font-size: 14px; -fx-font-weight: bold; -fx-alignment: center; -fx-background-color: transparent;");
                        }
                    }
                }
            });

            // Establecer el mismo estilo de fondo que la tabla
            column.setStyle("-fx-background-color: transparent;");

            // Agregar la columna a la tabla
            tableView.getColumns().add(column);
        }
    }

    /**
     * Muestra un laberinto en una tabla
     */
    private void displayMaze(TableView<ObservableList<String>> tableView, int[][] maze) {
        // Limpiar datos existentes
        tableView.getItems().clear();

        // Crear datos para cada fila
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        for (int row = 0; row < maze.length; row++) {
            ObservableList<String> rowData = FXCollections.observableArrayList();

            for (int col = 0; col < maze[row].length; col++) {
                rowData.add(String.valueOf(maze[row][col]));
            }

            data.add(rowData);
        }

        // Establecer los datos en la tabla
        tableView.setItems(data);
    }
}