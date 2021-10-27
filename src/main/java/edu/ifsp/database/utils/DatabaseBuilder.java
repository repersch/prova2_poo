package edu.ifsp.database.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseBuilder {

    public static void main(String[] args) {
        clear();
        build();
    }


    private static void build() {
        try (Statement stmt = ConnectionFactory.createStatement()) {
            stmt.addBatch("CREATE TABLE Pessoa (" +
                            "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                            "nome TEXT NOT NULL," +
                            "data_nascimento TEXT NOT NULL," +
                            "sexo TEXT NOT NULL," +
                            "rua TEXT," +
                            "numero TEXT," +
                            "bairro TEXT," +
                            "cidade TEXT," +
                            "estado TEXT," +
                            "amigo INTEGER NOT NULL," +
                            "coisa_preferida TEXT," +
                            "id_grupo INTEGER NOT NULL," +
                            "FOREIGN KEY (id_grupo) REFERENCES grupo(id))");

            stmt.addBatch("CREATE TABLE Grupo (" +
                            "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                            "nome TEXT NOT NULL)");

            stmt.executeBatch();
            stmt.close();
            System.out.println("Criando banco de dados...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void clear() {
        System.out.println("Apagando banco de dados...");
        try {
            Files.deleteIfExists(Paths.get("pessoa.db"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
