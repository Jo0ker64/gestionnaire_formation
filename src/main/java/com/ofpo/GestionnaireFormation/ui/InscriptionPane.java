package com.ofpo.GestionnaireFormation.ui;

import com.ofpo.GestionnaireFormation.DTO.InscriptionDTO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.springframework.web.client.RestTemplate;

public class InscriptionPane extends VBox {
    private final RestTemplate rest = new RestTemplate();
    private final String base = "http://localhost:8080/inscriptions";

    private final TableView<InscriptionDTO> table = new TableView<>();
    private final TextField tfUser = new TextField();
    private final TextField tfForm = new TextField();

    public InscriptionPane() {
        setSpacing(10);
        setPadding(new Insets(10));

        TableColumn<InscriptionDTO, Long> colU = new TableColumn<>("User ID");
        colU.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getUtilisateurId()));
        TableColumn<InscriptionDTO, Long> colF = new TableColumn<>("Formation ID");
        colF.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getFormationId()));

        table.getColumns().addAll(colU, colF);
        table.setItems(fetchAll());

        tfUser.setPromptText("User ID");
        tfForm.setPromptText("Formation ID");
        Button btnAdd = new Button("Inscrire");
        btnAdd.setOnAction(e -> {
            rest.postForObject(base + "/" + tfUser.getText() + "/" + tfForm.getText(), null, Void.class);
            table.setItems(fetchAll());
        });
        Button btnDel = new Button("Désinscrire");
        btnDel.setOnAction(e -> {
            rest.delete(base + "/" + tfUser.getText() + "/" + tfForm.getText());
            table.setItems(fetchAll());
        });

        HBox hb = new HBox(5, tfUser, tfForm, btnAdd, btnDel);
        getChildren().addAll(new Label("Inscriptions"), table, hb);
    }

    private ObservableList<InscriptionDTO> fetchAll() {
        InscriptionDTO[] arr = rest.getForObject(base + "/formation/0", InscriptionDTO[].class);
        return FXCollections.observableArrayList(arr);
    }
}