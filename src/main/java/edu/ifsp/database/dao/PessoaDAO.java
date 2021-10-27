package edu.ifsp.database.dao;

import edu.ifsp.database.utils.ConnectionFactory;
import edu.ifsp.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO implements GenericDAO<Colega, Integer> {

    @Override
    public void create(Colega colega) {
        String sql = "INSERT INTO Pessoa (nome, data_nascimento, sexo, rua, numero, bairro, cidade, estado, amigo, coisa_preferida, id_grupo)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            setDadosPessoa(colega, stmt);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Colega colega) {
        String sql = "UPDATE Pessoa SET nome = ?, data_nascimento = ?, sexo = ?, rua = ?, numero = ?, bairro = ?," +
                " cidade = ?, estado = ?, amigo = ?, coisa_preferida = ?, id_grupo = ? WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            setDadosPessoa(colega, stmt);

            stmt.setInt(12, colega.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void delete(Integer key) {
        String sql = "DELETE FROM Pessoa WHERE ID = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setInt(1, key);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Colega load(Integer key) {
        String sql = "SELECT * FROM Pessoa WHERE id = ?";
        Grupo grupo = null;
        Colega pessoa = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {

            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();

            String nome = rs.getString("nome");
            LocalDate nascimento = LocalDate.parse(rs.getString("data_nascimento"));
            String sexo = rs.getString("sexo");

            String rua = rs.getString("rua");
            String numero = rs.getString("numero");
            String bairro = rs.getString("bairro");
            String cidade = rs.getString("cidade");
            String estado = rs.getString("estado");
            Endereco endereco = new Endereco(rua, bairro, numero, cidade, estado);

            int idGrupo = rs.getInt("id_grupo");
            grupo = new GrupoDAO().load(idGrupo);

            int amigo = rs.getInt("amigo");
            String coisaPreferida = rs.getString("coisa_preferida");

            if (amigo == 1) {
                pessoa = new Amigo(key, nome, nascimento, Sexo.toEnum(sexo), endereco, grupo, coisaPreferida);
            } else {
                pessoa = new Colega(key, nome, nascimento, Sexo.toEnum(sexo), endereco, grupo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pessoa;
    }


    @Override
    public List<Colega> loadAll() {

        String sql = "SELECT * FROM Pessoa";
        List<Colega> colegas = new ArrayList<>();
        Grupo grupo = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                LocalDate nascimento = LocalDate.parse(rs.getString("data_nascimento"));
                String sexo = rs.getString("sexo");

                String rua = rs.getString("rua");
                String numero = rs.getString("numero");
                String bairro = rs.getString("bairro");
                String cidade = rs.getString("cidade");
                String estado = rs.getString("estado");
                Endereco endereco = new Endereco(rua, bairro, numero, cidade, estado);

                int idGrupo = rs.getInt("id_grupo");
                grupo = new GrupoDAO().load(idGrupo);

                int amigo = rs.getInt("amigo");
                String coisaPreferida = rs.getString("coisa_preferida");

                if (amigo == 1) {
                    colegas.add(new Amigo(id, nome, nascimento, Sexo.toEnum(sexo), endereco, grupo, coisaPreferida));
                } else {
                    colegas.add(new Colega(id, nome, nascimento, Sexo.toEnum(sexo), endereco, grupo));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colegas;
    }


    private void setDadosPessoa(Colega colega, PreparedStatement stmt) throws SQLException {

        stmt.setString(1, colega.getNome());
        stmt.setString(2, colega.getNascimento().toString());
        stmt.setString(3, colega.getSexo().toString());
        stmt.setString(4, colega.getEndereco().getRua());
        stmt.setString(5, colega.getEndereco().getNumero());
        stmt.setString(6, colega.getEndereco().getBairro());
        stmt.setString(7, colega.getEndereco().getCidade());
        stmt.setString(8, colega.getEndereco().getEstado());
        stmt.setInt(9, (colega instanceof Amigo) ? 1 : 0);
        if (colega instanceof Amigo) {
            stmt.setString(10, ((Amigo) colega).getCoisaPreferida());
        } else {
            stmt.setNull(10, Types.VARCHAR);
        }
        stmt.setInt(11, colega.getGrupo().getId());
    }
}

