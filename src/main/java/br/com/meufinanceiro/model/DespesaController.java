
package br.com.meufinanceiro.controller;

import br.com.meufinanceiro.model.Despesa;
import br.com.meufinanceiro.repository.DespesaRepository;
import br.com.meufinanceiro.service.DespesaService;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DespesaController {

    private final DespesaService service =
            new DespesaService(new DespesaRepository());

    @FXML private TextField descricaoField;
    @FXML private TextField valorField;
    @FXML private ComboBox<String> categoriaCombo;
    @FXML private ComboBox<String> pagamentoCombo;
    @FXML private DatePicker dataPicker;

    @FXML private TableView<Despesa> despesasTable;
    @FXML private TableColumn<Despesa, String> descricaoColumn;
    @FXML private TableColumn<Despesa, String> valorColumn;
    @FXML private TableColumn<Despesa, String> categoriaColumn;
    @FXML private TableColumn<Despesa, String> dataColumn;
    @FXML private TableColumn<Despesa, String> pagamentoColumn;

    private final DateTimeFormatter formatoData =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    public void initialize() {

        categoriaCombo.setItems(FXCollections.observableArrayList(
                "Moradia", "Transporte", "Pets",
                "Cuidados pessoais", "Lazer",
                "Saúde", "Assinaturas", "Outros"
        ));

        pagamentoCombo.setItems(FXCollections.observableArrayList(
                "Pix", "Débito", "Crédito", "Dinheiro"
        ));

        dataPicker.setValue(LocalDate.now());

        descricaoColumn.setCellValueFactory(
                cell -> new SimpleStringProperty(
                        cell.getValue().getDescricao()
                )
        );

        valorColumn.setCellValueFactory(
                cell -> new SimpleStringProperty(
                        "R$ " + cell.getValue().getValor()
                )
        );

        categoriaColumn.setCellValueFactory(
                cell -> new SimpleStringProperty(
                        cell.getValue().getCategoria()
                )
        );

        dataColumn.setCellValueFactory(
                cell -> new SimpleStringProperty(
                        cell.getValue().getData().format(formatoData)
                )
        );

        pagamentoColumn.setCellValueFactory(
                cell -> new SimpleStringProperty(
                        cell.getValue().getFormaPagamento()
                )
        );

        carregarDespesas();
    }

    @FXML
    private void salvarDespesa() {

        try {
            String valorTexto = valorField.getText()
                    .trim()
                    .replace(".", "")
                    .replace(",", ".");

            Despesa despesa = new Despesa(
                    descricaoField.getText(),
                    new BigDecimal(valorTexto),
                    categoriaCombo.getValue(),
                    dataPicker.getValue(),
                    pagamentoCombo.getValue()
            );

            service.salvar(despesa);

            carregarDespesas();
            limparFormulario();

        } catch (IllegalArgumentException e) {
            mostrarErro("Dados inválidos", e.getMessage());

        } catch (SQLException e) {
            mostrarErro("Erro ao salvar", e.getMessage());
        }
    }

    @FXML
    private void excluirDespesa() {

        Despesa selecionada =
                despesasTable.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            mostrarErro(
                    "Atenção",
                    "Selecione uma despesa para excluir."
            );
            return;
        }

        Alert confirmacao = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Deseja excluir a despesa selecionada?",
                ButtonType.YES,
                ButtonType.NO
        );

        if (confirmacao.showAndWait().orElse(ButtonType.NO)
                != ButtonType.YES) {
            return;
        }

        try {
            service.excluir(selecionada.getId());
            carregarDespesas();

        } catch (SQLException e) {
            mostrarErro("Erro ao excluir", e.getMessage());
        }
    }

    private void carregarDespesas() {
        try {
            despesasTable.setItems(
                    FXCollections.observableArrayList(service.listar())
            );
        } catch (SQLException e) {
            mostrarErro("Erro ao consultar despesas", e.getMessage());
        }
    }

    private void limparFormulario() {
        descricaoField.clear();
        valorField.clear();
        categoriaCombo.setValue(null);
        pagamentoCombo.setValue(null);
        dataPicker.setValue(LocalDate.now());
    }

    private void mostrarErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
