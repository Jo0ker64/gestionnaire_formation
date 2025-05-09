package com.ofpo.GestionnaireFormation.ui;

import com.ofpo.GestionnaireFormation.DTO.ModuleDTO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

public class ModulePane extends VBox {
    // Client HTTP pour appeler l’API REST
    private final RestTemplate rest = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/modules";

    // Vue en tableau des modules
    private final TableView<ModuleDTO> table = new TableView<>();

    // Champs du formulaire
    private final TextField tfLibelle = new TextField();

    public ModulePane() {
        // --- 1. Colonnes du tableau ---
        TableColumn<ModuleDTO, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getId())
        );
        TableColumn<ModuleDTO, String> colLib = new TableColumn<>("Libellé");
        colLib.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getLibelle())
        );

        table.getColumns().addAll(colId, colLib);
        table.setItems(fetchModules());

        // Lors d’une sélection : remplir le formulaire
        table.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> loadForm(nv));

        // --- 2. Boutons Nouveau/Supprimer ---
        Button btnNew = new Button("Nouveau");
        btnNew.setOnAction(e -> table.getSelectionModel().clearSelection());

        Button btnDel = new Button("Supprimer");
        btnDel.setOnAction(e -> {
            ModuleDTO sel = table.getSelectionModel().getSelectedItem();
            if (sel != null) {
                rest.delete(baseUrl + "/" + sel.getId());
                table.setItems(fetchModules());
            }
        });

        HBox hbButtons = new HBox(5, btnNew, btnDel);

        // --- 3. Formulaire de saisie ---
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(8);
        form.addRow(0, new Label("Libellé"), tfLibelle);

        Button btnSave = new Button("Enregistrer");
        btnSave.setOnAction(e -> saveOrUpdate());

        HBox hbForm = new HBox(10, form, btnSave);
        hbForm.setPadding(new Insets(10));

        // --- 4. Assemblage ---
        this.setSpacing(10);
        this.setPadding(new Insets(15));
        this.getChildren().addAll(
                new Label("Modules"),
                table,
                hbButtons,
                new Separator(),
                hbForm
        );
    }

    /** Récupère la liste des modules depuis l’API */
    private ObservableList<ModuleDTO> fetchModules() {
        try {
            ModuleDTO[] arr = rest.getForObject(baseUrl + "/", ModuleDTO[].class);
            return FXCollections.observableArrayList(arr);
        } catch (ResourceAccessException e) {
            // Si le back n'est pas prêt, on revient vide pour éviter le crash
            return FXCollections.observableArrayList();
        }
    }

    /** Remplit ou vide le formulaire selon la sélection */
    private void loadForm(ModuleDTO m) {
        if (m == null) {
            tfLibelle.clear();
        } else {
            tfLibelle.setText(m.getLibelle());
        }
    }

    /** Envoie POST (nouveau) ou PUT (modif) puis recharge */
    private void saveOrUpdate() {
        ModuleDTO sel = table.getSelectionModel().getSelectedItem();
        ModuleDTO dto = (sel == null) ? new ModuleDTO() : sel;
        dto.setLibelle(tfLibelle.getText());

        if (sel == null) {
            rest.postForObject(baseUrl + "/create", dto, ModuleDTO.class);
        } else {
            rest.put(baseUrl + "/" + dto.getId(), dto);
        }
        table.setItems(fetchModules());
    }
}
