package com.ofpo.GestionnaireFormation.ui;

import com.ofpo.GestionnaireFormation.DTO.SalleDTO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.springframework.web.client.RestTemplate;

public class SallePane extends VBox {
    private final RestTemplate rest = new RestTemplate();
    private final String base = "http://localhost:8080/salles";

    private final TableView<SalleDTO> table = new TableView<>();
    private final TextField tfLib = new TextField();
    private final TextField tfCentre = new TextField();

    public SallePane() {
        setSpacing(10); setPadding(new Insets(10));

        TableColumn<SalleDTO, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getId()));
        TableColumn<SalleDTO, String> colLib = new TableColumn<>("Libellé");
        colLib.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getLibelle()));
        TableColumn<SalleDTO, Long> colC = new TableColumn<>("Centre ID");
        colC.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getCentreId()));

        table.getColumns().addAll(colId, colLib, colC);
        table.setItems(fetchAll());
        table.getSelectionModel().selectedItemProperty().addListener((obs,old,nv)->{
            if(nv!=null) {
                tfLib.setText(nv.getLibelle());
                tfCentre.setText(nv.getCentreId().toString());
            }
        });

        Button btnSave = new Button("Enregistrer");
        btnSave.setOnAction(e -> {
            SalleDTO dto = table.getSelectionModel().getSelectedItem();
            boolean isNew = dto==null;
            if (isNew) dto = new SalleDTO();
            dto.setLibelle(tfLib.getText());
            dto.setCentreId(Long.valueOf(tfCentre.getText()));
            if (isNew) rest.postForObject(base + "/create/centre/" + dto.getCentreId(), dto, SalleDTO.class);
            else rest.put(base + "/update/" + dto.getId(), dto);
            table.setItems(fetchAll());
        });
        Button btnDel = new Button("Supprimer");
        btnDel.setOnAction(e -> {
            SalleDTO sel = table.getSelectionModel().getSelectedItem();
            if (sel!=null) rest.delete(base + "/delete/" + sel.getId());
            table.setItems(fetchAll());
        });

        HBox form = new HBox(5,
                new VBox(new Label("Libellé"), tfLib),
                new VBox(new Label("CentreId"), tfCentre),
                new VBox(btnSave, btnDel)
        );
        getChildren().addAll(new Label("Salles"), table, form);
    }

    private ObservableList<SalleDTO> fetchAll() {
        SalleDTO[] arr = rest.getForObject(base + "/", SalleDTO[].class);
        return FXCollections.observableArrayList(arr);
    }
}