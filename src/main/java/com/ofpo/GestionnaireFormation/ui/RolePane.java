package com.ofpo.GestionnaireFormation.ui;

import com.ofpo.GestionnaireFormation.DTO.RoleDTO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.springframework.web.client.RestTemplate;

public class RolePane extends VBox {
    private final RestTemplate rest = new RestTemplate();
    private final String base = "http://localhost:8080/roles";

    private final TableView<RoleDTO> table = new TableView<>();
    private final TextField tfLib = new TextField();

    public RolePane() {
        setSpacing(10); setPadding(new Insets(10));

        TableColumn<RoleDTO, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getId()));
        TableColumn<RoleDTO, String> colLib = new TableColumn<>("Libellé");
        colLib.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getLibelle()));
        table.getColumns().addAll(colId, colLib);
        table.setItems(fetchAll());
        table.getSelectionModel().selectedItemProperty().addListener((o,ov,nv)-> tfLib.setText(nv==null?"":nv.getLibelle()));

        Button btnSave = new Button("Enregistrer");
        btnSave.setOnAction(e -> {
            RoleDTO dto = table.getSelectionModel().getSelectedItem();
            boolean isNew = dto==null;
            if (isNew) dto = new RoleDTO();
            dto.setLibelle(tfLib.getText());
            if (isNew) rest.postForObject(base + "/create", dto, RoleDTO.class);
            else rest.put(base + "/update/" + dto.getId(), dto);
            table.setItems(fetchAll());
        });
        Button btnDel = new Button("Supprimer");
        btnDel.setOnAction(e -> {
            RoleDTO sel = table.getSelectionModel().getSelectedItem();
            if (sel!=null) rest.delete(base + "/delete/" + sel.getId());
            table.setItems(fetchAll());
        });

        HBox form = new HBox(5, tfLib, btnSave, btnDel);
        getChildren().addAll(new Label("Roles"), table, form);
    }

    private ObservableList<RoleDTO> fetchAll() {
        RoleDTO[] arr = rest.getForObject(base + "/", RoleDTO[].class);
        return FXCollections.observableArrayList(arr);
    }
}