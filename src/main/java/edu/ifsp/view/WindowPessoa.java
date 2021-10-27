package edu.ifsp.view;

import edu.ifsp.controller.ControllerPessoa;
import edu.ifsp.model.Colega;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowPessoa {

    public void showAndWait() throws IOException {
        showAndWait(null, false);
    }


    public void showAndWait(Colega pessoa, boolean vizualizar) throws IOException {

        FXMLLoader loader = new FXMLLoader();

        Pane graph = loader.load(getClass().getResource("WindowPessoa.fxml").openStream());
        ControllerPessoa controller = loader.getController();

        Scene scene = new Scene(graph, 445, 400);
        Stage stage = new Stage();

        if (pessoa == null) {
            stage.setTitle("Adicionar Pessoa");
        } else {
            stage.setTitle("Gerenciar Pessoa");
            controller.setDadosNaTela(pessoa);
        }

        if (vizualizar) {
            controller.visualizarPessoa();
        }

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
