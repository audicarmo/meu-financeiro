
package br.com.meufinanceiro.repository;

import br.com.meufinanceiro.config.DatabaseConfig;
import br.com.meufinanceiro.model.Despesa;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DespesaRepository {

    public void salvar(Despesa despesa) throws SQLException {

        String sql = """
                INSERT INTO despesa
                (descricao, valor_centavos, categoria,
                 data, forma_pagamento)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql, Statement.RETURN_GENERATED_KEYS)) {

            preencherParametros(statement, despesa);
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    despesa.setId(keys.getLong(1));
                }
            }
        }
    }

    public List<Despesa> listar() throws SQLException {

        String sql = """
                SELECT id, descricao, valor_centavos,
                       categoria, data, forma_pagamento
                FROM despesa
                ORDER BY data DESC, id DESC
                """;

        List<Despesa> despesas = new ArrayList<>();

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                despesas.add(mapearDespesa(result));
            }
        }

        return despesas;
    }

    public boolean atualizar(Despesa despesa) throws SQLException {

        String sql = """
                UPDATE despesa
                SET descricao = ?,
                    valor_centavos = ?,
                    categoria = ?,
                    data = ?,
                    forma_pagamento = ?
                WHERE id = ?
                """;

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            preencherParametros(statement, despesa);
            statement.setLong(6, despesa.getId());

            return statement.executeUpdate() > 0;
        }
    }

    public boolean excluir(Long id) throws SQLException {

        String sql = "DELETE FROM despesa WHERE id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        }
    }

    private void preencherParametros(
            PreparedStatement statement,
            Despesa despesa) throws SQLException {

        statement.setString(1, despesa.getDescricao());
        statement.setLong(
                2,
                despesa.getValor()
                        .movePointRight(2)
                        .longValueExact()
        );
        statement.setString(3, despesa.getCategoria());
        statement.setString(4, despesa.getData().toString());
        statement.setString(5, despesa.getFormaPagamento());
    }

    private Despesa mapearDespesa(ResultSet result)
            throws SQLException {

        Despesa despesa = new Despesa();

        despesa.setId(result.getLong("id"));
        despesa.setDescricao(result.getString("descricao"));

        despesa.setValor(
                BigDecimal.valueOf(
                        result.getLong("valor_centavos"), 2
                )
        );

        despesa.setCategoria(result.getString("categoria"));
        despesa.setData(
                LocalDate.parse(result.getString("data"))
        );
        despesa.setFormaPagamento(
                result.getString("forma_pagamento")
        );

        return despesa;
    }
}
