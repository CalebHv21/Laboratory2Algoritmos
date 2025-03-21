package controller;

import domain.NQueenProblem;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class QueensController {
    @javafx.fxml.FXML
    private TableView<ObservableList<String>> tvBoard;
    @javafx.fxml.FXML
    private ComboBox<String> cbSelectBoard;
    @javafx.fxml.FXML
    private TextArea taMessage;

    private NQueenProblem queensProblem;

    @javafx.fxml.FXML
    public void initialize() {
        // Inicializar el ComboBox con las opciones de tamaño de tablero
        cbSelectBoard.setItems(FXCollections.observableArrayList("4x4", "8x8"));

        // Seleccionar el primer tablero por defecto
        cbSelectBoard.getSelectionModel().select(0);

        // Crear columnas para la tabla
        createTableColumns(4); // Por defecto mostramos el tablero 4x4

        // Resolver y mostrar el primer tablero
        selectedBoard(null);
    }

    private void createTableColumns(int size) {
        // Limpiar columnas existentes
        tvBoard.getColumns().clear();

        // Crear columnas
        for (int i = 1; i <= size; i++) {
            final int colIndex = i - 1;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>("Col " + i);

            // Establecer ancho de columna para llenar la tabla uniformemente
            column.prefWidthProperty().bind(tvBoard.widthProperty().divide(size));

            // Usar cell value factory para mostrar los datos
            column.setCellValueFactory(param ->
                    new SimpleStringProperty(param.getValue().get(colIndex)));

            // Estilizar las celdas para mantener el fondo transparente
            column.setCellFactory(col -> new TableCell<ObservableList<String>, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null || item.isEmpty()) {
                        setText("");
                        setStyle("-fx-background-color: transparent;");
                    } else {
                        if (item.equals("1")) {
                            setText("♕"); // Símbolo Unicode de reina
                            setStyle("-fx-text-fill: black; -fx-font-size: 24px; -fx-font-weight: bold; -fx-alignment: center; -fx-background-color: transparent;");
                        } else {
                            setText(""); // No mostrar los ceros
                            setStyle("-fx-background-color: transparent;");
                        }
                    }
                }
            });

            // Establecer el mismo estilo de fondo que la tabla
            column.setStyle("-fx-background-color: transparent;");

            // Agregar la columna a la tabla
            tvBoard.getColumns().add(column);
        }
    }

    @javafx.fxml.FXML
    public void selectedBoard(ActionEvent actionEvent) {
        // Obtener el tamaño de tablero seleccionado
        String selected = cbSelectBoard.getValue();
        int size = (selected.equals("4x4")) ? 4 : 8;

        // Crear y resolver el problema de las N reinas
        queensProblem = new NQueenProblem(size);
        boolean solved = queensProblem.solve();

        // Recrear las columnas para el tamaño de tablero seleccionado
        createTableColumns(size);

        if (solved) {
            // Mostrar el tablero resuelto
            displayBoard(queensProblem.getBoard());

            // Mostrar mensaje de éxito más estético y corto
            taMessage.setText("✓ Solución para tablero " + size + "x" + size + "\n\n"
                    + "N-Reinas: " + size + " reinas colocadas sin amenazarse entre sí.\n"
                    + "Algoritmo: Backtracking recursivo.");
        } else {
            // Mostrar mensaje de error (no debería ocurrir para 4x4 y 8x8)
            taMessage.setText("No se pudo encontrar una solución para el tablero " + size + "x" + size + ".");
        }
    }

    private void displayBoard(int[][] board) {
        // Limpiar datos existentes
        tvBoard.getItems().clear();

        // Crear datos para cada fila
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        for (int row = 0; row < board.length; row++) {
            ObservableList<String> rowData = FXCollections.observableArrayList();

            for (int col = 0; col < board[row].length; col++) {
                rowData.add(String.valueOf(board[row][col]));
            }

            data.add(rowData);
        }

        // Establecer los datos en la tabla
        tvBoard.setItems(data);
    }


}