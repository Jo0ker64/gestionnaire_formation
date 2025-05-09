package com.ofpo.GestionnaireFormation.ui;

import com.ofpo.GestionnaireFormation.DTO.TypeDTO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

public class TypePane extends VBox {
    private final RestTemplate rest = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/types";

    private final TableView<TypeDTO> table = new TableView<>();
    private final TextField tfLibelle = new TextField();

    public TypePane() {
        // 1. Colonnes
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
                .addListener((obs, old, nv) -> loadForm(nv));

        // 2. Bouton Supprimer

        Button btnDel = new Button("Supprimer");
        btnDel.setOnAction(e -> {
            TypeDTO sel = table.getSelectionModel().getSelectedItem();
            if (sel != null) {
                rest.delete(baseUrl + "/delete/" + sel.getId());
                table.setItems(fetchTypes());
            }
        });
        HBox hbButtons = new HBox(5, btnDel);

        // 3. Formulaire
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(8);
        form.addRow(0, new Label("Libellé"), tfLibelle);

        Button btnSave = new Button("Enregistrer");
        btnSave.setOnAction(e -> saveOrUpdate());
        HBox hbSave = new HBox(btnSave);
        hbSave.setPadding(new Insets(5, 0, 0, 0));

        // 4. Assemblage
        setSpacing(10);
        setPadding(new Insets(15));
        getChildren().addAll(
                new Label("Types de Ressource"),
                table,
                hbButtons,
                new Separator(),
                form,
                hbSave
        );
    }

    private ObservableList<TypeDTO> fetchTypes() {
        try {
            TypeDTO[] arr = rest.getForObject(baseUrl + "/", TypeDTO[].class);
            return FXCollections.observableArrayList(arr);
        } catch (ResourceAccessException e) {
            // journalise l’erreur et renvoie une liste vide
            System.err.println("Impossible de joindre l’API /types : " + e.getMessage());
            return FXCollections.observableArrayList();
        }
    }

    private void loadForm(TypeDTO t) {
        tfLibelle.setText(t == null ? "" : t.getLibelle());
    }

    private void saveOrUpdate() {
        TypeDTO sel = table.getSelectionModel().getSelectedItem();
        TypeDTO dto = (sel == null) ? new TypeDTO() : sel;
        dto.setLibelle(tfLibelle.getText());

        if (sel == null) {
            rest.postForObject(baseUrl + "/create", dto, TypeDTO.class);
        } else {
            rest.put(baseUrl + "/update/" + dto.getId(), dto);
        }
        table.setItems(fetchTypes());
    }
}
