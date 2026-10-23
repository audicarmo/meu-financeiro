package br.com.meufinanceiro;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import br.com.meufinanceiro.config.DatabaseConfig;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        try (var connection = DatabaseConfig.getConnection()) {
            System.out.println("Banco SQLite conectado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
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