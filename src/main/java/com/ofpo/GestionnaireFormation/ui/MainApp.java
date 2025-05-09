package com.ofpo.GestionnaireFormation.ui;

import com.ofpo.GestionnaireFormation.DTO.*;
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

        // Onglet Modules
        tabs.getTabs().add(new Tab("Modules", new ModulePane()));

        // Onglet Types
        tabs.getTabs().add(new Tab("Types", new TypePane()));

        // Onglet Ressources
        tabs.getTabs().add(new Tab("Ressources", new RessourcePane()));

        // Onglet Utilisateurs
        tabs.getTabs().add(new Tab("Utilisateurs", new UtilisateurPane()));

        // Onglet Formations
        tabs.getTabs().add(new Tab("Formations", new FormationPane()));

        // Onglet Documents
        tabs.getTabs().add(new Tab("Documents", new DocumentPane()));

        // Toujours permettre de fermer un onglet si besoin
        tabs.getTabs().forEach(tab -> tab.setClosable(false));

        stage.setScene(new Scene(tabs, 800, 600));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
