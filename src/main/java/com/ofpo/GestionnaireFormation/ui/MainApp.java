package com.ofpo.GestionnaireFormation.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Gestionnaire de Formation - Vue JavaFX");

        // On crée un TabPane unique
        TabPane tabs = new TabPane();
        tabs.getTabs().add(new Tab("Centre de Formation", new CentrePane()));
        tabs.getTabs().add(new Tab("Documents",          new DocumentPane()));
        tabs.getTabs().add(new Tab("Formations",         new FormationPane()));
        tabs.getTabs().add(new Tab("Modules",            new ModulePane()));
        tabs.getTabs().add(new Tab("Ressources",         new RessourcePane()));
        tabs.getTabs().add(new Tab("Roles",              new RolePane()));
        tabs.getTabs().add(new Tab("Salles",             new SallePane()));
        tabs.getTabs().add(new Tab("Séquences",          new SequencePane()));
        tabs.getTabs().add(new Tab("Types",              new TypePane()));
        tabs.getTabs().add(new Tab("Utilisateurs",       new UtilisateurPane()));

        // Interdire la fermeture accidentelle des onglets
        tabs.getTabs().forEach(tab -> tab.setClosable(false));

        // Création de la scène et application du style
        Scene scene = new Scene(tabs, 1000, 800);
        scene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm()
        );

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
