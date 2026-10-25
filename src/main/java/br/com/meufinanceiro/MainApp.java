package br.com.meufinanceiro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import br.com.meufinanceiro.config.DatabaseConfig;
import br.com.meufinanceiro.config.DatabaseInitializer;
import javafx.scene.control.Alert;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        try {
            DatabaseInitializer.initialize();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de inicialização");
            alert.setHeaderText(
                    "Não foi possível iniciar o banco de dados."
            );
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(
                MainApp.class.getResource("despesas.fxml")
        );

        Parent root = loader.load();

        Scene scene = new Scene(root, 1000, 700);

        scene.getStylesheets().add(
                MainApp.class.getResource("styles.css")
                        .toExternalForm()
        );

        stage.setTitle("Meu Financeiro");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}