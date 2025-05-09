package com.ofpo.GestionnaireFormation.ui;

import com.ofpo.GestionnaireFormation.DTO.DocumentDTO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.springframework.web.client.RestTemplate;

public class DocumentPane extends VBox {
    // 1. Client HTTP pour appeler l’API REST
    private final RestTemplate rest = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/documents";

    // 2. TableView pour afficher la liste des documents
    private final TableView<DocumentDTO> table = new TableView<>();

    // 3. Champs du formulaire de saisie / modification
    private final TextField tfLibelle    = new TextField();
    private final DatePicker dpCreation  = new DatePicker();
    private final CheckBox cbActif       = new CheckBox("Actif");

    public DocumentPane() {
        // --- A. Colonnes du tableau ---
        TableColumn<DocumentDTO, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getId())
        );

        TableColumn<DocumentDTO, String> colLib = new TableColumn<>("Libellé");
        colLib.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getLibelle())
        );

        TableColumn<DocumentDTO, java.time.LocalDate> colDate = new TableColumn<>("Date création");
        colDate.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getDateCreation())
        );


        table.getColumns().addAll(colId, colLib, colDate);
        table.setItems(fetchDocuments());

        // Au clic sur une ligne, charger le formulaire
        table.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> loadForm(sel));

        // --- B. Boutons Nouveau / Supprimer ---
        Button btnAdd = new Button("Nouveau");
        Button btnDel = new Button("Supprimer");
        btnAdd.setOnAction(e -> table.getSelectionModel().clearSelection());
        btnDel.setOnAction(e -> {
            DocumentDTO d = table.getSelectionModel().getSelectedItem();
            if (d != null) {
                rest.delete(baseUrl + "/" + d.getId());
                table.setItems(fetchDocuments());
            }
        });
        HBox hbBtns = new HBox(5, btnAdd, btnDel);

        // --- C. Formulaire ---
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(8);
        form.addRow(0, new Label("Libellé"), tfLibelle);
        form.addRow(1, new Label("Date création"), dpCreation);

        Button btnSave = new Button("Enregistrer");
        btnSave.setOnAction(e -> saveOrUpdate());

        // --- D. Assemblage de la vue ---
        this.setSpacing(10);
        this.setPadding(new Insets(15));
        this.getChildren().addAll(
                new Label("Documents"),
                table,
                hbBtns,
                new Separator(),
                form,
                btnSave
        );
    }

    /** Récupère tous les documents via l’API */
    private ObservableList<DocumentDTO> fetchDocuments() {
        DocumentDTO[] arr = rest.getForObject(baseUrl + "/", DocumentDTO[].class);
        return FXCollections.observableArrayList(arr);
    }

    /** Remplit ou vide le formulaire selon la sélection */
    private void loadForm(DocumentDTO d) {
        if (d == null) {
            tfLibelle.clear();
            dpCreation.setValue(null);
        } else {
            tfLibelle.setText(d.getLibelle());
            dpCreation.setValue(d.getDateCreation());
        }
    }

    /** POST si création, PUT si mise à jour, puis rafraîchit */
    private void saveOrUpdate() {
        DocumentDTO d = table.getSelectionModel().getSelectedItem();
        boolean isNew = (d == null);
        if (isNew) d = new DocumentDTO();

        d.setLibelle(tfLibelle.getText());
        d.setDateCreation(dpCreation.getValue());


        if (isNew) {
            rest.postForObject(baseUrl + "/create", d, DocumentDTO.class);
        } else {
            rest.put(baseUrl + "/" + d.getId(), d);
        }

        table.setItems(fetchDocuments());
    }
}
