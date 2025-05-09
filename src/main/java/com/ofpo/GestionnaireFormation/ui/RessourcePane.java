package com.ofpo.GestionnaireFormation.ui;

import com.ofpo.GestionnaireFormation.DTO.RessourceDTO;
import com.ofpo.GestionnaireFormation.DTO.TypeDTO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

public class RessourcePane extends VBox {
    // Client HTTP pour appeler l’API REST
    private final RestTemplate rest = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/ressources";
    private final String typesUrl = "http://localhost:8080/types";

    // TableView des ressources
    private final TableView<RessourceDTO> table = new TableView<>();

    // Champs du formulaire
    private final TextField tfLibelle         = new TextField();
    private final DatePicker dpDateCreation   = new DatePicker();
    private final DatePicker dpDateModif      = new DatePicker();
    private final ComboBox<TypeDTO> cbType    = new ComboBox<>();

    public RessourcePane() {
        // --- 1. Configuration des colonnes ---
        TableColumn<RessourceDTO, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getId())
        );

        TableColumn<RessourceDTO, String> colLib = new TableColumn<>("Libellé");
        colLib.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getLibelle())
        );

        TableColumn<RessourceDTO, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(
                        c.getValue().getType() == null
                                ? ""
                                : c.getValue().getType().getLibelle()
                )
        );

        table.getColumns().addAll(colId, colLib, colType);
        table.setItems(fetchRessources());
        table.getSelectionModel().selectedItemProperty()
                .addListener((o, ov, nv) -> loadForm(nv));

        // --- 2. Boutons Nouveau / Supprimer ---
        Button btnNew = new Button("Nouveau");
        btnNew.setOnAction(e -> table.getSelectionModel().clearSelection());

        Button btnDel = new Button("Supprimer");
        btnDel.setOnAction(e -> {
            RessourceDTO sel = table.getSelectionModel().getSelectedItem();
            if (sel != null) {
                rest.delete(baseUrl + "/" + sel.getId());
                table.setItems(fetchRessources());
            }
        });

        HBox hbButtons = new HBox(5, btnNew, btnDel);

        // --- 3. Formulaire de saisie / modification ---
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(8);

        form.addRow(0, new Label("Libellé"), tfLibelle);
        form.addRow(1, new Label("Création"), dpDateCreation);
        form.addRow(2, new Label("Modification"), dpDateModif);
        form.addRow(3, new Label("Type"), cbType);

        // Charger la liste des types dans la combo
        cbType.setItems(fetchTypes());

        Button btnSave = new Button("Enregistrer");
        btnSave.setOnAction(e -> saveOrUpdate());

        HBox hbFormBtn = new HBox(btnSave);
        hbFormBtn.setPadding(new Insets(10, 0, 0, 0));

        // --- 4. Assemblage de la vue ---
        this.setSpacing(10);
        this.setPadding(new Insets(15));
        this.getChildren().addAll(
                new Label("Ressources"),
                table,
                hbButtons,
                new Separator(),
                form,
                hbFormBtn
        );
    }

    /** Récupère toutes les ressources depuis l'API */
    private ObservableList<RessourceDTO> fetchRessources() {
        try {
            RessourceDTO[] arr = rest.getForObject(baseUrl + "/", RessourceDTO[].class);
            return FXCollections.observableArrayList(arr);
        } catch (ResourceAccessException e) {
            return FXCollections.observableArrayList();
        }
    }

    /** Récupère tous les types depuis l'API */
    private ObservableList<TypeDTO> fetchTypes() {
        try {
            TypeDTO[] arr = rest.getForObject(typesUrl + "/", TypeDTO[].class);
            return FXCollections.observableArrayList(arr);
        } catch (ResourceAccessException e) {
            return FXCollections.observableArrayList();
        }
    }

    /** Remplit ou vide le formulaire selon la sélection */
    private void loadForm(RessourceDTO r) {
        if (r == null) {
            tfLibelle.clear();
            dpDateCreation.setValue(null);
            dpDateModif.setValue(null);
            cbType.getSelectionModel().clearSelection();
        } else {
            tfLibelle.setText(r.getLibelle());
            dpDateCreation.setValue(r.getDateCreation());
            dpDateModif.setValue(r.getDateModification());
            cbType.getSelectionModel().select(r.getType());
        }
    }

    /** Envoie POST (nouvelle) ou PUT (mise à jour) puis recharge la table */
    private void saveOrUpdate() {
        RessourceDTO sel = table.getSelectionModel().getSelectedItem();
        RessourceDTO dto = (sel == null) ? new RessourceDTO() : sel;

        dto.setLibelle(tfLibelle.getText());
        dto.setDateCreation(dpDateCreation.getValue());
        dto.setDateModification(dpDateModif.getValue());
        dto.setType(cbType.getValue());

        if (sel == null) {
            rest.postForObject(baseUrl + "/create", dto, RessourceDTO.class);
        } else {
            rest.put(baseUrl + "/" + dto.getId(), dto);
        }
        table.setItems(fetchRessources());
    }
}
