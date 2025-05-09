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
    private final TextField tfNom         = new TextField();
    private final TextField tfAdresse     = new TextField();
    private final TextField tfVille       = new TextField();
    private final TextField tfCodePostal  = new TextField();
    private final TextField tfTel         = new TextField();

    public CentrePane() {
        // 1. Colonnes
        TableColumn<CentreDTO, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getId()));
        TableColumn<CentreDTO, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getNom()));
        TableColumn<CentreDTO, String> colAddr = new TableColumn<>("Adresse");
        colAddr.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getAdressePostal()));
        TableColumn<CentreDTO, String> colVille = new TableColumn<>("Ville");
        colVille.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getVille()));
        TableColumn<CentreDTO, String> colCP = new TableColumn<>("Code Postal");
        colCP.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getCodePostal()));
        TableColumn<CentreDTO, String> colTel = new TableColumn<>("Téléphone");
        colTel.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getTelephone()));

        table.getColumns().addAll(colId, colNom, colAddr, colVille, colCP, colTel);
        table.setItems(fetchCentres());
        table.getSelectionModel().selectedItemProperty().addListener((obs, old, nv) -> loadForm(nv));

        // 2. Boutons Supprimer
        Button btnDel = new Button("Supprimer");
        btnDel.setOnAction(e -> {
            CentreDTO sel = table.getSelectionModel().getSelectedItem();
            if (sel != null) {
                rest.delete(base + "/delete/" + sel.getId());
                table.setItems(fetchCentres());
            }
        });
        HBox hbBtns = new HBox(5, btnDel);

        // 3. Formulaire
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(8);
        form.addRow(0, new Label("Nom"),         tfNom);
        form.addRow(1, new Label("Adresse"),     tfAdresse);
        form.addRow(2, new Label("Ville"),       tfVille);
        form.addRow(3, new Label("Code Postal"),tfCodePostal);
        form.addRow(4, new Label("Téléphone"),   tfTel);

        Button btnSave = new Button("Enregistrer");
        btnSave.setOnAction(e -> saveOrUpdate());

        this.setSpacing(10);
        this.setPadding(new Insets(15));
        this.getChildren().addAll(
                new Label("Centres de Formation"),
                table,
                hbBtns,
                new Separator(),
                form,
                btnSave
        );
    }

    /** Récupère tous les centres via l’API */
    private ObservableList<CentreDTO> fetchCentres() {
        CentreDTO[] arr = rest.getForObject(base + "/", CentreDTO[].class);
        return FXCollections.observableArrayList(arr);
    }

    /** Remplit ou vide le formulaire selon la sélection */
    private void loadForm(CentreDTO c) {
        if (c == null) {
            tfNom.clear();
            tfAdresse.clear();
            tfVille.clear();
            tfCodePostal.clear();
            tfTel.clear();
        } else {
            tfNom.setText(c.getNom());
            tfAdresse.setText(c.getAdressePostal());
            tfVille.setText(c.getVille());
            tfCodePostal.setText(c.getCodePostal());
            tfTel.setText(c.getTelephone());
        }
    }

    /** Envoie la requête POST (nouveau) ou PUT (mise à jour) */
    private void saveOrUpdate() {
        CentreDTO dto = table.getSelectionModel().getSelectedItem();
        boolean isNew = (dto == null);
        if (isNew) dto = new CentreDTO();

        dto.setNom(tfNom.getText());
        dto.setAdressePostal(tfAdresse.getText());
        dto.setVille(tfVille.getText());
        dto.setCodePostal(tfCodePostal.getText());
        dto.setTelephone(tfTel.getText());

        if (isNew) {
            rest.postForObject(base + "/create", dto, CentreDTO.class);
        } else {
            rest.put(base + "/update/" + dto.getId(), dto);
        }
        table.setItems(fetchCentres());
    }
}
