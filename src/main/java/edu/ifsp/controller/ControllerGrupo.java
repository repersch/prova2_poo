package edu.ifsp.controller;

import edu.ifsp.database.dao.GrupoDAO;
import edu.ifsp.model.Grupo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;

public class ControllerGrupo {

    @FXML
    private TableView<Grupo> tableViewGrupo;
    @FXML
    private TableColumn<Grupo, Integer> cId;
    @FXML
    private TableColumn<Grupo, String> cNome;
    @FXML
    private TextField txtNome;

    private ObservableList<Grupo> tableGrupos;
    private Grupo grupoSelecionado = null;

    @FXML
    private void initialize() {
        cId.setCellValueFactory(new PropertyValueFactory<>("id"));
        cNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableGrupos = FXCollections.observableArrayList();
        tableViewGrupo.setItems(tableGrupos);
        carregarTableView();
    }

    private void carregarTableView() {
        GrupoDAO dao = new GrupoDAO();
        List<Grupo> grupos = dao.loadAll();
        tableGrupos.clear();
        tableGrupos.addAll(grupos);
    }

    public void selecionarGrupo(MouseEvent mouseEvent) {
        this.grupoSelecionado = tableViewGrupo.getSelectionModel().getSelectedItem();
        setGrupoSelecionado();
    }

    public void setGrupoSelecionado() {
         if (grupoSelecionado != null) {
             txtNome.setText(grupoSelecionado.getNome());
         }
    }

    public void salvarOuAtualizarGrupo(ActionEvent actionEvent) {
        if (grupoSelecionado != null) {
            atualizarGrupo(grupoSelecionado);
        } else {
            salvarGrupo();
        }

        recarregarTableView();
    }

    private void recarregarTableView() {
        txtNome.setText("");
        carregarTableView();
    }

    private void salvarGrupo() {
        GrupoDAO dao = new GrupoDAO();
        Grupo grupo = new Grupo(0, txtNome.getText());
        dao.create(grupo);
    }

    private void atualizarGrupo(Grupo grupoSelecionado) {
        if (grupoSelecionado != null) {
            GrupoDAO dao = new GrupoDAO();
            grupoSelecionado.setNome(txtNome.getText());
            dao.update(grupoSelecionado);
        }
    }

    public void excluirGrupo(ActionEvent actionEvent) {
        GrupoDAO dao = new GrupoDAO();
        dao.delete(grupoSelecionado.getId());
        recarregarTableView();
    }

    public void cancelar(ActionEvent actionEvent) {
        Stage stage = (Stage) txtNome.getScene().getWindow();
        stage.close();
    }
}
