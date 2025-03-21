package controller;

import domain.SudokuSolver;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class SudokuController {
    @javafx.fxml.FXML
    private TableView<ObservableList<String>> tvInitialBoard;
    @javafx.fxml.FXML
    private TableView<ObservableList<String>> tvSolutionBoard;

    private SudokuSolver sudokuSolver;
    private int[][][][] boards; // boards[0] = tableros iniciales, boards[1] = tableros solución
    private static final int NUM_BOARDS = 5;
    @javafx.fxml.FXML
    private ComboBox cbSelectBoard;

    @javafx.fxml.FXML
    public void initialize() {
        // Inicializar el solucionador de sudoku
        sudokuSolver = new SudokuSolver();

        // Generar espacio para los 5 tableros
        boards = new int[2][NUM_BOARDS][9][9];

        // Llenar los tableros iniciales
        fillInitialBoards();

        // Inicializar el ComboBox
        ObservableList<String> boardOptions = FXCollections.observableArrayList();
        for (int i = 1; i <= NUM_BOARDS; i++) {
            boardOptions.add("Board" + i);
        }
        cbSelectBoard.setItems(boardOptions);

        // Crear columnas para ambas tablas
        createTableColumns(tvInitialBoard);
        createTableColumns(tvSolutionBoard);

        // Seleccionar el primer tablero por defecto
        cbSelectBoard.getSelectionModel().select(0);
        selectedBoard(null);
    }

    /**
     * Llena los tableros iniciales y sus soluciones
     */
    private void fillInitialBoards() {
        // Tablero 1
        int[][] board1 = {
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

        // Tablero 2
        int[][] board2 = {
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

        // Tablero 3
        int[][] board3 = {
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

        // Tablero 4
        int[][] board4 = {
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

        // Tablero 5
        int[][] board5 = {
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

        // Copiar tableros iniciales a la estructura de datos
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boards[0][0][i][j] = board1[i][j];
                boards[0][1][i][j] = board2[i][j];
                boards[0][2][i][j] = board3[i][j];
                boards[0][3][i][j] = board4[i][j];
                boards[0][4][i][j] = board5[i][j];
            }
        }

        // Resolver cada tablero y guardar las soluciones
        for (int b = 0; b < NUM_BOARDS; b++) {
            // Copiar el tablero inicial a la solución
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    boards[1][b][i][j] = boards[0][b][i][j];
                }
            }

            // Resolver el tablero
            sudokuSolver.solveBoard(boards[1][b]);
        }
    }

    private void createTableColumns(TableView<ObservableList<String>> tableView) {
        // Limpiar columnas existentes
        tableView.getColumns().clear();

        // Crear columnas
        for (int i = 1; i <= 9; i++) {
            final int colIndex = i - 1;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>("Col " + i);

            // Establecer ancho de columna para llenar la tabla uniformemente
            column.prefWidthProperty().bind(tableView.widthProperty().divide(9));

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
                        setText(item);
                        // Estilo para coincidir con el fondo de gradiente radial de la tabla
                        setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-alignment: center; -fx-background-color: transparent;");
                    }
                }
            });

            // Establecer el mismo estilo de fondo que la tabla (gradiente radial)
            column.setStyle("-fx-background-color: transparent;");

            // Agregar la columna a la tabla
            tableView.getColumns().add(column);
        }
    }

    @javafx.fxml.FXML
    public void selectedBoard(ActionEvent actionEvent) {
        // Obtener el índice del tablero seleccionado
        int boardIndex = cbSelectBoard.getSelectionModel().getSelectedIndex();

        if (boardIndex >= 0 && boardIndex < NUM_BOARDS) {
            // Mostrar el tablero inicial
            displayBoard(tvInitialBoard, boards[0][boardIndex]);

            // Mostrar el tablero de solución
            displayBoard(tvSolutionBoard, boards[1][boardIndex]);
        }
    }

    private void displayBoard(TableView<ObservableList<String>> tableView, int[][] board) {
        // Limpiar datos existentes
        tableView.getItems().clear();

        // Crear datos para cada fila
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        for (int row = 0; row < 9; row++) {
            ObservableList<String> rowData = FXCollections.observableArrayList();

            for (int col = 0; col < 9; col++) {
                // Convertir valor int a String (0 será celda vacía)
                rowData.add(board[row][col] == 0 ? "" : String.valueOf(board[row][col]));
            }

            data.add(rowData);
        }

        // Establecer los datos en la tabla
        tableView.setItems(data);
    }
}