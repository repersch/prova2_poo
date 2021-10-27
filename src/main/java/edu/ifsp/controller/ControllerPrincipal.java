package edu.ifsp.controller;

import edu.ifsp.database.dao.PessoaDAO;
import edu.ifsp.model.*;
import edu.ifsp.view.WindowGrupo;
import edu.ifsp.view.WindowPessoa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ControllerPrincipal {
    
    @FXML
    private TableView<Colega> tableView;
    @FXML
    private TableColumn<Grupo, String> cGrupo;
    @FXML
    private TableColumn<Colega, String> cNome;
    @FXML
    private TableColumn<Colega, String> cNascimento;
    @FXML
    private TableColumn<Colega, String> cSexo;
    @FXML
    private Label lbCoisaPreferida;
    @FXML
    private TextField txtFiltrar;
    
    private ObservableList<Colega> pessoas;
    public Colega pessoaSelecionada;

    @FXML
    public void initialize() {

        cGrupo.setCellValueFactory(new PropertyValueFactory<>("grupo"));
        cNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cNascimento.setCellValueFactory(new PropertyValueFactory<>("nascimento"));
        cSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        pessoas = FXCollections.observableArrayList();
        tableView.setItems(pessoas);

        carregarTabela();
        filtrarDadosDaTabela();
    }


    private void filtrarDadosDaTabela() {
        FilteredList<Colega> dadosFiltrados = new FilteredList<>(pessoas, p -> true);
        txtFiltrar.textProperty().addListener((observado, valorAntigo, valorNovo) -> {
            dadosFiltrados.setPredicate(pessoa -> {
                if (valorNovo == null || valorNovo.isEmpty()) {
                    return true;
                }

                String filtroEmLowerCase = valorNovo.toLowerCase();

                if (pessoa.getNome().toLowerCase().contains(filtroEmLowerCase)) {
                    return true;
                } else if (pessoa.getSexo().toString().toLowerCase().contains(filtroEmLowerCase)) {
                    return true;
                } else if (pessoa.getGrupo().getNome().toLowerCase().contains(filtroEmLowerCase)) {
                    return true;
                } else return pessoa.getNascimento().toString().contains(filtroEmLowerCase);
            });
        });

        SortedList<Colega> dadosBuscados = new SortedList<>(dadosFiltrados);
        dadosBuscados.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(dadosBuscados);
    }


    private void carregarTabela() {
        PessoaDAO dao = new PessoaDAO();
        List<Colega> todoMundo = dao.loadAll();
        pessoas.clear();
        pessoas.addAll(todoMundo);
    }


    public void gerenciarGrupo(ActionEvent actionEvent) {
        WindowGrupo windowGrupo = new WindowGrupo();

        try {
            windowGrupo.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        carregarTabela();
    }


    public void addPessoa(ActionEvent actionEvent) {
        WindowPessoa windowPessoa = new WindowPessoa();

        try {
            windowPessoa.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        carregarTabela();
    }


    public void visualizarPessoa(ActionEvent actionEvent) {
        WindowPessoa windowPessoa = new WindowPessoa();

        pessoaSelecionada = tableView.getSelectionModel().getSelectedItem();

        if (pessoaSelecionada == null) {
            throw new PessoaNaoSelecionadaException("É necessário selecionar uma linha da tabela para visualizar.");
        } else {
            try {
                windowPessoa.showAndWait(pessoaSelecionada, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void editarPessoa(ActionEvent actionEvent) {
        WindowPessoa windowPessoa = new WindowPessoa();
        pessoaSelecionada = tableView.getSelectionModel().getSelectedItem();

        if (pessoaSelecionada == null) {
            throw new PessoaNaoSelecionadaException("É necessário selecionar uma linha da tabela para editar.");
        } else {
            try {
                windowPessoa.showAndWait(pessoaSelecionada, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            carregarTabela();
        }
    }


    public void excluirPessoa(ActionEvent actionEvent) {
        pessoaSelecionada = tableView.getSelectionModel().getSelectedItem();

        if (pessoaSelecionada == null) {
            throw new PessoaNaoSelecionadaException("É necessário selecionar uma linha da tabela para excluir.");
        } else {
            PessoaDAO dao = new PessoaDAO();
            dao.delete(pessoaSelecionada.getId());
            carregarTabela();
        }
    }


    public void mostrarCoisaPreferida(MouseEvent mouseEvent) {
        pessoaSelecionada = tableView.getSelectionModel().getSelectedItem();

        if (pessoaSelecionada instanceof Amigo) {

            StringBuilder dicaDePresente = new StringBuilder();
            dicaDePresente.append(pessoaSelecionada.getSexo() == Sexo.FEMININO ? "A " : "O ");
            dicaDePresente.append(pessoaSelecionada.getNome());
            dicaDePresente.append(" gosta muito de ");
            dicaDePresente.append(((Amigo) pessoaSelecionada).getCoisaPreferida());
            dicaDePresente.append("!");

            lbCoisaPreferida.setText(dicaDePresente.toString().toUpperCase());
        } else {
            lbCoisaPreferida.setText("");
        }
    }
}
