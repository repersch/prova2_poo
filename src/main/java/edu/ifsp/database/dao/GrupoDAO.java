package edu.ifsp.database.dao;

import edu.ifsp.database.utils.ConnectionFactory;
import edu.ifsp.model.Grupo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GrupoDAO implements GenericDAO<Grupo, Integer> {

    @Override
    public void create(Grupo grupo) {
        String sql = "INSERT INTO Grupo (nome) VALUES (?)";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, grupo.getNome());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Grupo grupo) {
        String sql = "UPDATE Grupo SET nome = ? WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, grupo.getNome());
            stmt.setInt(2, grupo.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void delete(Integer key) {
        String sql = "DELETE FROM Grupo WHERE id = ?";

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, key);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Grupo load(Integer key) {
        String sql = "SELECT * FROM Grupo WHERE id = ?";
        Grupo grupo = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                grupo = new Grupo(id, nome);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grupo;
    }


    @Override
    public List<Grupo> loadAll() {
        String sql = "SELECT * FROM Grupo";
        List<Grupo> grupos = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                grupos.add(new Grupo(id, nome));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grupos;
    }
}
