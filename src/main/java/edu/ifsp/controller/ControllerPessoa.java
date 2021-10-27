package edu.ifsp.controller;

import edu.ifsp.database.dao.GrupoDAO;
import edu.ifsp.database.dao.PessoaDAO;
import edu.ifsp.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;


public class ControllerPessoa {

    @FXML
    private TextField txtNome;
    @FXML
    private ChoiceBox<Grupo> cbGrupo;
    @FXML
    private CheckBox ckAmigo;
    @FXML
    private RadioButton rbFeminino;
    @FXML
    private RadioButton rbMasculino;
    @FXML
    private DatePicker dpDataNascimento;
    @FXML
    private TextField txtCoisaPreferida;
    @FXML
    private TextField txtRua;
    @FXML
    private TextField txtNumero;
    @FXML
    private TextField txtCidade;
    @FXML
    private TextField txtBairro;
    @FXML
    private TextField txtEstado;
    @FXML
    private Button btnSalvar;

    private Endereco endereco;
    public Colega pessoaSelecionada;

    @FXML
    public void initialize() {
        ObservableList<Grupo> grupos = FXCollections.observableArrayList(new GrupoDAO().loadAll());
        grupos.addAll();
        cbGrupo.setItems(grupos);
    }


    public void voltar(ActionEvent actionEvent) {
        Stage stage = (Stage) txtNome.getScene().getWindow();
        stage.close();
    }


    public void salvaOuAtualizaPessoa(ActionEvent actionEvent) {
        if (pessoaSelecionada != null) {
            atualizarPessoa(getDadosDaTela());
        } else {
            salvarPessoa(getDadosDaTela());
        }
        Stage stage = (Stage) txtNome.getScene().getWindow();
        stage.close();
    }


    private void salvarPessoa(Colega pessoa) {
        PessoaDAO dao = new PessoaDAO();
        dao.create(pessoa);
    }


    private void atualizarPessoa(Colega pessoaSelecionada) {
        PessoaDAO dao = new PessoaDAO();
        dao.update(pessoaSelecionada);
    }


    private Colega getDadosDaTela() {

        final String nome = txtNome.getText();
        final int idGrupo = cbGrupo.getValue().getId();
        final LocalDate nascimento = dpDataNascimento.getValue();
        final Sexo sexo = rbFeminino.isSelected() ? Sexo.FEMININO : Sexo.MASCULINO;
        final String rua = txtRua.getText();
        final String numero = txtNumero.getText();
        final String bairro = txtBairro.getText();
        final String cidade = txtCidade.getText();
        final String estado = txtEstado.getText();
        final int amigo = ckAmigo.isSelected() ? 1 : 0;
        final String coisaPreferida = txtCoisaPreferida.getText();

        final int id = (pessoaSelecionada != null) ? pessoaSelecionada.getId() : 0;

        Endereco endereco = new Endereco(rua, bairro, numero, cidade, estado);
        Grupo grupo = new GrupoDAO().load(idGrupo);

        if (amigo == 1) {
            return new Amigo(id, nome, nascimento, sexo, endereco, grupo, coisaPreferida);
        } else return new Colega(id, nome, nascimento, sexo, endereco, grupo);
    }


    public void setDadosNaTela(Colega pessoaSelecionada) {
        this.pessoaSelecionada = pessoaSelecionada;
        txtNome.setText(pessoaSelecionada.getNome());
        cbGrupo.setValue(pessoaSelecionada.getGrupo());
        dpDataNascimento.setValue(pessoaSelecionada.getNascimento());
        if ((pessoaSelecionada.getSexo() == Sexo.FEMININO)) {
            rbFeminino.setSelected(true);
        } else {
            rbMasculino.setSelected(true);
        }
        txtRua.setText(pessoaSelecionada.getEndereco().getRua());
        txtNumero.setText(pessoaSelecionada.getEndereco().getNumero());
        txtBairro.setText(pessoaSelecionada.getEndereco().getBairro());
        txtCidade.setText(pessoaSelecionada.getEndereco().getCidade());
        txtEstado.setText(pessoaSelecionada.getEndereco().getEstado());
        if (pessoaSelecionada instanceof Amigo) {
            ckAmigo.setSelected(true);
            txtCoisaPreferida.setText(((Amigo) pessoaSelecionada).getCoisaPreferida());
        } else {
            txtCoisaPreferida.setDisable(true);
        }
    }


    public void visualizarPessoa() {
        btnSalvar.setVisible(false);
        txtNome.setDisable(true);
        cbGrupo.setDisable(true);
        dpDataNascimento.setDisable(true);
        rbMasculino.setDisable(true);
        rbFeminino.setDisable(true);
        txtRua.setDisable(true);
        txtNumero.setDisable(true);
        txtBairro.setDisable(true);
        txtCidade.setDisable(true);
        txtEstado.setDisable(true);
        ckAmigo.setDisable(true);
        txtCoisaPreferida.setDisable(true);
    }


    public void configurarCoisaPreferida(ActionEvent actionEvent) {
        txtCoisaPreferida.setDisable(!ckAmigo.isSelected());
    }
}
