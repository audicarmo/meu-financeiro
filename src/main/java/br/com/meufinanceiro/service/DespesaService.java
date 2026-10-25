
package br.com.meufinanceiro.service;

import br.com.meufinanceiro.model.Despesa;
import br.com.meufinanceiro.repository.DespesaRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class DespesaService {

    private final DespesaRepository repository;

    public DespesaService(DespesaRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    public void salvar(Despesa despesa) throws SQLException {
        validar(despesa);
        repository.salvar(despesa);
    }

    public List<Despesa> listar() throws SQLException {
        return repository.listar();
    }

    public boolean atualizar(Despesa despesa)
            throws SQLException {

        validar(despesa);

        if (despesa.getId() == null) {
            throw new IllegalArgumentException(
                    "Informe o ID da despesa."
            );
        }

        return repository.atualizar(despesa);
    }

    public boolean excluir(Long id) throws SQLException {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "ID da despesa inválido."
            );
        }

        return repository.excluir(id);
    }

    private void validar(Despesa despesa) {

        if (despesa == null) {
            throw new IllegalArgumentException(
                    "A despesa não pode ser nula."
            );
        }

        if (despesa.getDescricao() == null ||
                despesa.getDescricao().isBlank()) {
            throw new IllegalArgumentException(
                    "A descrição é obrigatória."
            );
        }

        if (despesa.getValor() == null ||
                despesa.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "O valor deve ser maior que zero."
            );
        }

        if (despesa.getValor().scale() > 2) {
            throw new IllegalArgumentException(
                    "O valor deve ter no máximo duas casas decimais."
            );
        }

        if (despesa.getCategoria() == null ||
                despesa.getCategoria().isBlank()) {
            throw new IllegalArgumentException(
                    "A categoria é obrigatória."
            );
        }

        if (despesa.getData() == null) {
            throw new IllegalArgumentException(
                    "A data é obrigatória."
            );
        }

        if (despesa.getFormaPagamento() == null ||
                despesa.getFormaPagamento().isBlank()) {
            throw new IllegalArgumentException(
                    "A forma de pagamento é obrigatória."
            );
        }
    }
}
