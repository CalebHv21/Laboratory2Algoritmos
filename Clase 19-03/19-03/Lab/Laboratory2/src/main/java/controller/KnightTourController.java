package controller;

import domain.KnightTour;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

import javax.swing.text.TableView;
import java.awt.*;
import java.util.Arrays;

public class KnightTourController {


    @javafx.fxml.FXML
    private javafx.scene.control.TableView<ObservableList<String>> ktSolutionBoard;
    @javafx.fxml.FXML
    private TextArea ktTextArea;

    // Tamaño del tablero
   private int N = 8;
   private int[][] sol = new int[N][N];  // Solución del Knight's Tour

    // Movimientos posibles del caballo
    private int[] xMove = {2, 1, -1, -2, -2, -1, 1, 2};
    private int[] yMove = {1, 2, 2, 1, -1, -2, -2, -1};




    @javafx.fxml.FXML
    public void initialize() {

        // Crear columnas para ambas tablas
        createTableColumns(ktSolutionBoard);

        this.ktTextArea.setText(printSolution());


    }


    public String printSolution() {

        KnightTour kt = new KnightTour();
        String result = "";

        result += "-- KNIGHT TOUR PROBLEM SOLUTION --\n";
        kt.solveKnightTour();
        result += kt.printSolution();

        return result;

    }



    private void createTableColumns(javafx.scene.control.TableView<ObservableList<String>> tableView) {
        // Limpiar columnas existentes
        tableView.getColumns().clear();

        // Crear columnas
        for (int i = 1; i <= 8; i++) {
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
    }//End createTableColumns








}//END CONTROLLER
