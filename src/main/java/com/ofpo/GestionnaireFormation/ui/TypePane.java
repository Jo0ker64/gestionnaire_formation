package com.ofpo.GestionnaireFormation.ui;

import com.ofpo.GestionnaireFormation.DTO.TypeDTO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.springframework.web.client.RestTemplate;

public class TypePane extends VBox {
    // Client HTTP pour appeler l'API REST
    private final RestTemplate rest = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/types";

    // TableView pour afficher les types
    private final TableView<TypeDTO> table = new TableView<>();

    // Champs du formulaire
    private final TextField tfLibelle = new TextField();

    public TypePane() {
        // --- 1. Configuration des colonnes ---
        TableColumn<TypeDTO, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getId())
        );

        TableColumn<TypeDTO, String> colLib = new TableColumn<>("Libellé");
        colLib.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getLibelle())
        );

        table.getColumns().addAll(colId, colLib);
        table.setItems(fetchTypes());
        table.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> loadForm(newV));

        // --- 2. Boutons Nouveau / Supprimer ---
        Button btnNew = new Button("Nouveau");
        btnNew.setOnAction(e -> table.getSelectionModel().clearSelection());

        Button btnDel = new Button("Supprimer");
        btnDel.setOnAction(e -> {
            TypeDTO sel = table.getSelectionModel().getSelectedItem();
            if (sel != null) {
                rest.delete(baseUrl + "/" + sel.getId()); // Suppression de l'élément DELETE
                table.setItems(fetchTypes());
            }
        });

        HBox hbButtons = new HBox(5, btnNew, btnDel);

        // --- 3. Formulaire de saisie / modification ---
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(8);
        form.addRow(0, new Label("Libellé"), tfLibelle);

        Button btnSave = new Button("Enregistrer");
        btnSave.setOnAction(e -> saveOrUpdate());

        HBox hbSave = new HBox(btnSave);
        hbSave.setPadding(new Insets(5, 0, 0, 0));

        // --- 4. Assemblage de la vue ---
        this.setSpacing(10);
        this.setPadding(new Insets(15));
        this.getChildren().addAll(
                new Label("Types de Ressource"),
                table,
                hbButtons,
                new Separator(),
                form,
                hbSave
        );
    }

    /** Récupère tous les types depuis l'API */
    private ObservableList<TypeDTO> fetchTypes() {
        TypeDTO[] arr = rest.getForObject(baseUrl + "/", TypeDTO[].class);
        return FXCollections.observableArrayList(arr);
    }

    /** Charge ou vide le formulaire selon la sélection */
    private void loadForm(TypeDTO t) {
        if (t == null) {
            tfLibelle.clear();
        } else {
            tfLibelle.setText(t.getLibelle());
        }
    }

    /** Envoie POST pour création ou PUT pour mise à jour, puis recharge la table */
    private void saveOrUpdate() {
        TypeDTO sel = table.getSelectionModel().getSelectedItem();
        TypeDTO dto = (sel == null) ? new TypeDTO() : sel;

        dto.setLibelle(tfLibelle.getText());

        if (sel == null) {
            // POST pour créer un nouveau type CREATE
            rest.postForObject(baseUrl + "/create", dto, TypeDTO.class);
        } else {
            rest.put(baseUrl + "/" + dto.getId(), dto);
        }

        table.setItems(fetchTypes());
    }
}
