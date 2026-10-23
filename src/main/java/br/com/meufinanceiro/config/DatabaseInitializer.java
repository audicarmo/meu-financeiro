
package br.com.meufinanceiro.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() throws SQLException {

        String sql = """
                CREATE TABLE IF NOT EXISTS despesa (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    descricao TEXT NOT NULL,
                    valor NUMERIC NOT NULL,
                    categoria TEXT NOT NULL,
                    data TEXT NOT NULL,
                    forma_pagamento TEXT NOT NULL
                )
                """;

        try (Connection connection =
                     DatabaseConfig.getConnection();
             Statement statement =
                     connection.createStatement()) {

            statement.execute(sql);
        }
    }
}
