package com.ofpo.GestionnaireFormation.ui;

import com.ofpo.GestionnaireFormation.DTO.CentreDTO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.springframework.web.client.RestTemplate;

public class CentrePane extends VBox {
    private final RestTemplate rest = new RestTemplate();
    private final String base = "http://localhost:8080/centres";

    private final TableView<CentreDTO> table = new TableView<>();
    private final TextField tfNom = new TextField();
    private final TextField tfAdresse = new TextField();

    public CentrePane() {
        setSpacing(10);
        setPadding(new Insets(10));

        TableColumn<CentreDTO, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getId()));
        TableColumn<CentreDTO, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getNom()));
        TableColumn<CentreDTO, String> colAddr = new TableColumn<>("Adresse");
        colAddr.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getAdressePostal()));

        table.getColumns().addAll(colId, colNom, colAddr);
        table.setItems(fetchAll());
        table.getSelectionModel().selectedItemProperty().addListener((o,ov,nv)->loadForm(nv));

        Button btnAdd = new Button("Nouveau");
        Button btnDel = new Button("Supprimer");
        btnAdd.setOnAction(e -> table.getSelectionModel().clearSelection());
        btnDel.setOnAction(e -> {
            CentreDTO sel = table.getSelectionModel().getSelectedItem();
            if (sel!=null) {
                rest.delete(base + "/delete/" + sel.getId());
                table.setItems(fetchAll());
            }
        });
        HBox hb = new HBox(5, btnAdd, btnDel);

        GridPane form = new GridPane();
        form.setHgap(10); form.setVgap(8);
        form.addRow(0, new Label("Nom"), tfNom);
        form.addRow(1, new Label("Adresse"), tfAdresse);

        Button btnSave = new Button("Enregistrer");
        btnSave.setOnAction(e -> saveOrUpdate());

        getChildren().addAll(new Label("Centres"), table, hb, new Separator(), form, btnSave);
    }

    private ObservableList<CentreDTO> fetchAll() {
        CentreDTO[] arr = rest.getForObject(base + "/", CentreDTO[].class);
        return FXCollections.observableArrayList(arr);
    }

    private void loadForm(CentreDTO c) {
        if (c==null) {
            tfNom.clear(); tfAdresse.clear();
        } else {
            tfNom.setText(c.getNom());
            tfAdresse.setText(c.getAdressePostal());
        }
    }

    private void saveOrUpdate() {
        CentreDTO dto = table.getSelectionModel().getSelectedItem();
        boolean isNew = dto==null;
        if (isNew) dto = new CentreDTO();
        dto.setNom(tfNom.getText());
        dto.setAdressePostal(tfAdresse.getText());
        if (isNew)
            rest.postForObject(base + "/create", dto, CentreDTO.class);
        else
            rest.put(base + "/update/" + dto.getId(), dto);
        table.setItems(fetchAll());
    }
}
