package com.example.crudabw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.*;

public class Controller {
    static final String DB_URL = "jdbc:mysql://localhost/usuarios";
    static final String USER = "root";
    static final String PASS = "";


    @FXML
    private TextField IdIngreso;

    @FXML
    private TextField edadIngreso;

    @FXML
    private TextField nombreIngreso;

    private boolean data = false;

    @FXML
    void crearBoton(ActionEvent event) {
        String Id = IdIngreso.getText();
        String nombre = nombreIngreso.getText();
        String edad = edadIngreso.getText();

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO datos (Id, nombre, edad) VALUES (?, ?, ?)");

            pstmt.setInt(1, Integer.parseInt(Id));
            pstmt.setString(2, nombre);
            pstmt.setInt(3, Integer.parseInt(edad));

            pstmt.executeUpdate();

            showAlert("Registro guardado correctamente.", Alert.AlertType.INFORMATION);

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error al guardar el registro.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Mensaje");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void actualizarBoton(ActionEvent event) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            if (!IdIngreso.getText().isEmpty() && !edadIngreso.getText().isEmpty() && !nombreIngreso.getText().isEmpty()) {
                String idString = IdIngreso.getText();
                String nombre = nombreIngreso.getText();
                String edadString = edadIngreso.getText();

                int id = Integer.parseInt(idString);
                int edad = Integer.parseInt(edadString);

                PreparedStatement sts = conn.prepareStatement("UPDATE datos SET nombre = ?, edad = ? WHERE id = ?");
                sts.setString(1, nombre);
                sts.setInt(2, edad);
                sts.setInt(3, id);
                sts.executeUpdate();  // Ejecuta la actualización en la base de datos

                showAlert("Registro actualizado correctamente", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Por favor, complete todos los campos", Alert.AlertType.ERROR);
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            showAlert("Error al actualizar registro", Alert.AlertType.ERROR);
        }
    }
    @FXML
    void buscarBoton(ActionEvent event) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement sts = conn.createStatement();
            ResultSet rs =sts.executeQuery("SELECT * FROM usuarios.datos");
            while(rs.next()) {
                if (IdIngreso.getText().equals(rs.getString("id"))) {
                    nombreIngreso.setText(rs.getString("nombre"));
                    edadIngreso.setText(rs.getString("edad"));
                    data = false;
                    break;
                }
                else {
                    data = true;
                }
            }
            if(data == true ) {
                showAlert("Error Registro NO encontrado", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void eliminarBoton(ActionEvent event) {
        String codigo = IdIngreso.getText();

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM datos WHERE Id = ?");

            pstmt.setInt(1, Integer.parseInt(codigo));

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Registro eliminado correctamente.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("No se encontró ningún registro con el código proporcionado.", Alert.AlertType.WARNING);
            }

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error al eliminar el registro.", Alert.AlertType.ERROR);
        }
    }
}