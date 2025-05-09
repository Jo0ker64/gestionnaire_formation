package com.ofpo.GestionnaireFormation.ui;

import com.ofpo.GestionnaireFormation.DTO.RoleDTO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.web.client.RestTemplate;

public class RolePane extends VBox {

    private final RestTemplate rest = new RestTemplate();
    private final String base = "http://localhost:8080/roles";

    private final TableView<RoleDTO> table = new TableView<>();
    private final TextField txtLibelle = new TextField();
    private final CheckBox cbActif = new CheckBox("Actif");
    private final Button btnSave   = new Button("Enregistrer");
    private final Button btnDelete = new Button("Supprimer");

    public RolePane() {
        setSpacing(10);
        setPadding(new Insets(10));

        TableColumn<RoleDTO, Long> colId  = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getId()));
        TableColumn<RoleDTO, String> colLib = new TableColumn<>("Libellé");
        colLib.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getLibelle()));
        TableColumn<RoleDTO, Boolean> colStat = new TableColumn<>("Actif");
        colStat.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getStatut()));

        table.getColumns().addAll(colId, colLib, colStat);
        table.setItems(fetchAll());

        // Quand on sélectionne un rôle, on remplit le formulaire
        table.getSelectionModel().selectedItemProperty().addListener((o,ov,nv)-> {
            if(nv == null) {
                txtLibelle.clear();
                cbActif.setSelected(true);
            } else {
                txtLibelle.setText(nv.getLibelle());
                cbActif.setSelected(nv.getStatut());
            }
        });

        txtLibelle.setPromptText("Libellé");
        cbActif.setSelected(true);

        btnSave.setOnAction(e -> saveOrUpdate());
        btnDelete.setOnAction(e -> deleteSelected());

        HBox form = new HBox(10,
                new VBox(new Label("Libellé"), txtLibelle),
                new VBox(cbActif),
                new VBox(btnSave, btnDelete)
        );

        getChildren().addAll(new Label("Rôles"), table, form);
    }

    private ObservableList<RoleDTO> fetchAll() {
        RoleDTO[] arr = rest.getForObject(base + "/", RoleDTO[].class);
        return FXCollections.observableArrayList(arr);
    }

    private void saveOrUpdate() {
        RoleDTO sel = table.getSelectionModel().getSelectedItem();
        RoleDTO dto = new RoleDTO();
        if (sel != null) dto.setId(sel.getId());
        dto.setLibelle(txtLibelle.getText());
        dto.setStatut(cbActif.isSelected());

        if (sel == null) {
            rest.postForObject(base + "/", dto, RoleDTO.class);
        } else {
            rest.put(base + "/" + dto.getId(), dto);
        }
        table.setItems(fetchAll());
    }

    private void deleteSelected() {
        RoleDTO sel = table.getSelectionModel().getSelectedItem();
        if (sel != null) {
            rest.delete(base + "/" + sel.getId());
            table.setItems(fetchAll());
        }
    }
}
