package com.ofpo.GestionnaireFormation.ui;

import com.ofpo.GestionnaireFormation.DTO.FormationDTO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

public class FormationPane extends VBox {
    // Client HTTP pour appeler l'API REST
    private final RestTemplate rest = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/formations";

    // TableView pour afficher la liste des formations
    private final TableView<FormationDTO> table = new TableView<>();

    // Champs du formulaire de saisie / modification
    private final TextField tfLibelle      = new TextField();
    private final TextField tfNumeroOffre  = new TextField();
    private final DatePicker dpDebut       = new DatePicker();
    private final DatePicker dpFin         = new DatePicker();
    private final DatePicker dpDebutPe     = new DatePicker();
    private final DatePicker dpFinPe       = new DatePicker();
    private final CheckBox cbStatut        = new CheckBox("Actif");

    public FormationPane() {
        // --- 1. Configuration des colonnes du tableau ---
        TableColumn<FormationDTO, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getId())
        );

        TableColumn<FormationDTO, String> colLib = new TableColumn<>("Libellé");
        colLib.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getLibelle())
        );

        TableColumn<FormationDTO, String> colOffre = new TableColumn<>("Offre");
        colOffre.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getNumeroOffre())
        );

        TableColumn<FormationDTO, LocalDate> colDeb = new TableColumn<>("Début");
        colDeb.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getDateDebut())
        );

        TableColumn<FormationDTO, LocalDate> colFin = new TableColumn<>("Fin");
        colFin.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getDateFin())
        );

        TableColumn<FormationDTO, LocalDate> colDebPe = new TableColumn<>("Début PE");
        colDebPe.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getDateDebutPe())
        );

        TableColumn<FormationDTO, LocalDate> colFinPe = new TableColumn<>("Fin PE");
        colFinPe.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getDateFinPe())
        );

        TableColumn<FormationDTO, Boolean> colStat = new TableColumn<>("Actif");
        colStat.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getStatut())
        );

        // Ajout des colonnes à la table et chargement des données
        table.getColumns().addAll(
                colId, colLib, colOffre,
                colDeb, colFin, colDebPe, colFinPe, colStat
        );
        table.setItems(fetchFormations());

        // Lorsque l'utilisateur sélectionne une ligne, remplir le formulaire
        table.getSelectionModel()
                .selectedItemProperty()
                .addListener((o, ov, nv) ->
                        loadForm(nv));

        // --- 2. Boutons Nouveau et Supprimer ---
        Button btnAdd = new Button("Nouveau");
        Button btnDel = new Button("Supprimer");

        // Nouveau = vider la sélection pour créer une nouvelle formation
        btnAdd.setOnAction(e ->
                table.getSelectionModel().clearSelection()
        );

        // Supprimer = appeler DELETE puis rafraîchir la liste
        btnDel.setOnAction(e -> {
            FormationDTO f = table.getSelectionModel().getSelectedItem();
            if (f != null) {
                rest.delete(baseUrl + "/" + f.getId());
                table.setItems(fetchFormations());
            }
        });

        HBox hbBtns = new HBox(5, btnAdd, btnDel);

        // --- 3. Formulaire de saisie / modification ---
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(8);

        // Disposition des labels et champs
        form.addRow(0, new Label("Libellé"),    tfLibelle);
        form.addRow(1, new Label("Offre"),      tfNumeroOffre);
        form.addRow(2, new Label("Date début"), dpDebut);
        form.addRow(3, new Label("Date fin"),   dpFin);
        form.addRow(4, new Label("Début PE"),   dpDebutPe);
        form.addRow(5, new Label("Fin PE"),     dpFinPe);
        form.add(cbStatut, 1, 6);  // case à cocher pour le statut

        // Bouton Enregistrer
        Button btnSave = new Button("Enregistrer");
        btnSave.setOnAction(e -> saveOrUpdate());

        // --- 4. Assemblage de la vue ---
        this.setSpacing(10);
        this.setPadding(new Insets(15));
        this.getChildren().addAll(
                new Label("Formations"),
                table,
                hbBtns,
                new Separator(),
                form,
                btnSave
        );
    }

    /**
     * Appelle l'API pour récupérer toutes les formations.
     */
    private ObservableList<FormationDTO> fetchFormations() {
        FormationDTO[] arr = rest.getForObject(
                baseUrl + "/", FormationDTO[].class
        );
        return FXCollections.observableArrayList(arr);
    }

    /**
     * Remplit le formulaire avec les données de la formation sélectionnée,
     * ou le vide si aucune sélection.
     */
    private void loadForm(FormationDTO f) {
        if (f == null) {
            tfLibelle.clear();
            tfNumeroOffre.clear();
            dpDebut.setValue(null);
            dpFin.setValue(null);
            dpDebutPe.setValue(null);
            dpFinPe.setValue(null);
            cbStatut.setSelected(true);
        } else {
            tfLibelle.setText(f.getLibelle());
            tfNumeroOffre.setText(f.getNumeroOffre());
            dpDebut.setValue(f.getDateDebut());
            dpFin.setValue(f.getDateFin());
            dpDebutPe.setValue(f.getDateDebutPe());
            dpFinPe.setValue(f.getDateFinPe());
            cbStatut.setSelected(f.getStatut());
        }
    }

    /**
     * Envoie un POST si nouvelle formation, ou PUT si modification,
     * puis rafraîchit la liste.
     */
    private void saveOrUpdate() {
        FormationDTO f = table.getSelectionModel().getSelectedItem();
        boolean isNew = (f == null);
        if (isNew) {
            f = new FormationDTO();
        }

        // Lecture des valeurs du formulaire
        f.setLibelle(tfLibelle.getText());
        f.setNumeroOffre(tfNumeroOffre.getText());
        f.setDateDebut(dpDebut.getValue());
        f.setDateFin(dpFin.getValue());
        f.setDateDebutPe(dpDebutPe.getValue());
        f.setDateFinPe(dpFinPe.getValue());
        f.setStatut(cbStatut.isSelected());

        // Appel POST ou PUT selon le contexte
        if (isNew) {
            rest.postForObject(
                    baseUrl + "/create",
                    f,
                    FormationDTO.class
            );
        } else {
            rest.put(
                    baseUrl + "/" + f.getId(),
                    f
            );
        }

        // Rechargement de la table
        table.setItems(fetchFormations());
    }
}
