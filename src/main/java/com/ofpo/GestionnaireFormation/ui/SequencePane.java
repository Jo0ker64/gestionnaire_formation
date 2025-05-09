package com.ofpo.GestionnaireFormation.ui;

import com.ofpo.GestionnaireFormation.DTO.SequenceDTO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.springframework.web.client.RestTemplate;

public class SequencePane extends VBox {
    private final RestTemplate rest = new RestTemplate();
    private final String base = "http://localhost:8080/sequences";

    private final TableView<SequenceDTO> table = new TableView<>();
    private final TextField tfLib = new TextField();
    private final TextField tfDuree = new TextField();

    public SequencePane() {
        setSpacing(10); setPadding(new Insets(10));

        TableColumn<SequenceDTO, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getId()));
        TableColumn<SequenceDTO, String> colLib = new TableColumn<>("Libellé");
        colLib.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getLibelle()));

        table.getColumns().addAll(colId, colLib);
        table.setItems(fetchAll());
        table.getSelectionModel().selectedItemProperty().addListener((obs,old,nv)->{
            if(nv!=null) {
                tfLib.setText(nv.getLibelle());
            }
        });

        Button btnSave = new Button("Enregistrer");
        btnSave.setOnAction(e -> {
            SequenceDTO dto = table.getSelectionModel().getSelectedItem();
            boolean isNew = dto==null;
            if (isNew) dto = new SequenceDTO();
            dto.setLibelle(tfLib.getText());
            if (isNew) rest.postForObject(base + "/create", dto, SequenceDTO.class);
            else rest.put(base + "/update/" + dto.getId(), dto);
            table.setItems(fetchAll());
        });
        Button btnDel = new Button("Supprimer");
        btnDel.setOnAction(e -> {
            SequenceDTO sel = table.getSelectionModel().getSelectedItem();
            if (sel!=null) rest.delete(base + "/delete/" + sel.getId());
            table.setItems(fetchAll());
        });

        HBox form = new HBox(5,
                new VBox(new Label("Libellé"), tfLib),
                new VBox(new Label("Durée"), tfDuree),
                new VBox(btnSave, btnDel)
        );
        getChildren().addAll(new Label("Sequences"), table, form);
    }

    private ObservableList<SequenceDTO> fetchAll() {
        SequenceDTO[] arr = rest.getForObject(base + "/", SequenceDTO[].class);
        return FXCollections.observableArrayList(arr);
    }
}