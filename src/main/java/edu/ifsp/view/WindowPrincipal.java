package edu.ifsp.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class WindowPrincipal extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane graph = FXMLLoader.load(getClass().getResource("WindowPrincipal.fxml"));
        Scene scene = new Scene(graph, 600, 400);

        stage.setTitle("Lista de Pessoas");
        stage.setScene(scene);
        stage.show();
    }

}