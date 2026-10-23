package br.com.meufinanceiro;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import br.com.meufinanceiro.config.DatabaseConfig;
import br.com.meufinanceiro.config.DatabaseInitializer;
import javafx.scene.control.Alert;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        try {
            DatabaseInitializer.initialize();
            System.out.println("Banco de dados inicializado com sucesso!");

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de inicialização");
            alert.setHeaderText("Não foi possível iniciar o banco de dados.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

            throw new IllegalStateException(
                    "Falha ao inicializar o banco de dados.", e
            );
        }

        Label titulo = new Label("Meu Financeiro");
        titulo.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;"
        );

        Label subtitulo = new Label(
                "Seu controle financeiro começa aqui!"
        );

        VBox layout = new VBox(15, titulo, subtitulo);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        Scene scene = new Scene(layout, 900, 600);

        stage.setTitle("Meu Financeiro");
        stage.setScene(scene);
        stage.setMinWidth(700);
        stage.setMinHeight(450);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}