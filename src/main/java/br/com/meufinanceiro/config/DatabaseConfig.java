package br.com.meufinanceiro.config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;

public class DatabaseConfig {

    private static final Path DB_DIR = Paths.get(
            System.getProperty("user.home"),
            ".meu-financeiro"
    );

    private static final Path DB_PATH =
            DB_DIR.resolve("financeiro.db");

    public static Connection getConnection()
            throws SQLException {

        try {
            Files.createDirectories(DB_DIR);
        } catch (IOException e) {
            throw new SQLException(
                    "Erro ao criar diretório do banco de dados.",
                    e
            );
        }

        return DriverManager.getConnection(
                "jdbc:sqlite:" + DB_PATH.toAbsolutePath()
        );
    }
}
