package com.ofpo.GestionnaireFormation.ui;

import com.ofpo.GestionnaireFormation.DTO.UtilisateurDTO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class UtilisateurPane extends VBox {
    private final RestTemplate rest = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/utilisateurs";

    // TableView et champs du formulaire
    private final TableView<UtilisateurDTO> table = new TableView<>();
    private final TextField tfMatricule       = new TextField();
    private final TextField tfAvatar          = new TextField();
    private final TextField tfNom             = new TextField();
    private final TextField tfPrenom          = new TextField();
    private final TextField tfMail            = new TextField();
    private final TextField tfAdressePostal   = new TextField();
    private final TextField tfCodePostal      = new TextField();
    private final TextField tfVille           = new TextField();
    private final TextField tfMotDePasse      = new TextField();
    private final TextField tfRoles           = new TextField();
    private final CheckBox cbActif            = new CheckBox("Actif");

    public UtilisateurPane() {
        // --- 1. Colonnes de la table ---
        TableColumn<UtilisateurDTO, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getId())
        );

        TableColumn<UtilisateurDTO, String> colMat = new TableColumn<>("Matricule");
        colMat.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getMatricule())
        );

        TableColumn<UtilisateurDTO, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getNom())
        );

        TableColumn<UtilisateurDTO, String> colPren = new TableColumn<>("Prénom");
        colPren.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getPrenom())
        );

        TableColumn<UtilisateurDTO, String> colMail = new TableColumn<>("Mail");
        colMail.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getAdresseMail())
        );

        TableColumn<UtilisateurDTO, Boolean> colAct = new TableColumn<>("Actif");
        colAct.setCellValueFactory(c ->
                new ReadOnlyObjectWrapper<>(c.getValue().getStatut())
        );

        table.getColumns().addAll(colId, colMat, colNom, colPren, colMail, colAct);
        table.setItems(fetchUsers());
        table.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> loadForm(newV));

        // --- 2. Boutons Nouveau / Supprimer ---
        Button btnNew = new Button("Nouveau");
        btnNew.setOnAction(e -> table.getSelectionModel().clearSelection());

        Button btnDel = new Button("Supprimer");
        btnDel.setOnAction(e -> {
            UtilisateurDTO sel = table.getSelectionModel().getSelectedItem();
            if (sel != null) {
                rest.delete(baseUrl + "/" + sel.getId());
                table.setItems(fetchUsers());
            }
        });
        HBox hbButtons = new HBox(5, btnNew, btnDel);

        // --- 3. Formulaire complet ---
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(8);

        form.addRow(0, new Label("Matricule"),       tfMatricule);
        form.addRow(1, new Label("Avatar URL"),      tfAvatar);
        form.addRow(2, new Label("Nom"),             tfNom);
        form.addRow(3, new Label("Prénom"),          tfPrenom);
        form.addRow(4, new Label("Mail"),            tfMail);
        form.addRow(5, new Label("Adresse postal"),  tfAdressePostal);
        form.addRow(6, new Label("Code postal"),     tfCodePostal);
        form.addRow(7, new Label("Ville"),           tfVille);
        form.addRow(8, new Label("Mot de passe"),    tfMotDePasse);
        form.addRow(9, new Label("Rôles (CSV)"),     tfRoles);

        form.add(new Label("Actif"), 0, 10);
        form.add(cbActif, 1, 10);

        // --- 4. Bouton Enregistrer ---
        Button btnSave = new Button("Enregistrer");
        btnSave.setOnAction(e -> saveOrUpdate());

        HBox hbSave = new HBox(btnSave);
        hbSave.setPadding(new Insets(5, 0, 0, 0));

        // --- 5. Assemblage final ---
        this.setSpacing(10);
        this.setPadding(new Insets(15));
        this.getChildren().addAll(
                new Label("Utilisateurs"),
                table,
                hbButtons,
                new Separator(),
                form,
                hbSave
        );
    }

    /** GET /utilisateurs/ */
    private ObservableList<UtilisateurDTO> fetchUsers() {
        UtilisateurDTO[] arr = rest.getForObject(baseUrl + "/", UtilisateurDTO[].class);
        return FXCollections.observableArrayList(arr);
    }

    /** Remplit le formulaire avec l’utilisateur sélectionné */
    private void loadForm(UtilisateurDTO u) {
        if (u == null) {
            tfMatricule.clear();
            tfAvatar.clear();
            tfNom.clear();
            tfPrenom.clear();
            tfMail.clear();
            tfAdressePostal.clear();
            tfCodePostal.clear();
            tfVille.clear();
            tfMotDePasse.clear();
            tfRoles.clear();
            cbActif.setSelected(true);
        } else {
            tfMatricule.setText(u.getMatricule());
            tfAvatar.setText(u.getAvatar());
            tfNom.setText(u.getNom());
            tfPrenom.setText(u.getPrenom());
            tfMail.setText(u.getAdresseMail());
            tfAdressePostal.setText(u.getAdressePostal());
            tfCodePostal.setText(u.getCodePostal());
            tfVille.setText(u.getVille());
            tfMotDePasse.setText(u.getMotDePasse());
            tfRoles.setText(String.join(",", u.getRoles())); // ou autre format
            cbActif.setSelected(u.getStatut());
        }
    }

    /** POST /utilisateurs/create ou PUT /utilisateurs/{id} */
    private void saveOrUpdate() {
        UtilisateurDTO sel = table.getSelectionModel().getSelectedItem();
        boolean isNew = sel == null;
        UtilisateurDTO dto = isNew ? new UtilisateurDTO() : sel;

        dto.setMatricule(tfMatricule.getText());
        dto.setAvatar(tfAvatar.getText());
        dto.setNom(tfNom.getText());
        dto.setPrenom(tfPrenom.getText());
        dto.setAdresseMail(tfMail.getText());
        dto.setAdressePostal(tfAdressePostal.getText());
        dto.setCodePostal(tfCodePostal.getText());
        dto.setVille(tfVille.getText());
        dto.setMotDePasse(tfMotDePasse.getText());

        // convertir le CSV en liste
        dto.setRoles(List.of(tfRoles.getText().split("\\s*,\\s*")));
        dto.setStatut(cbActif.isSelected());

        if (isNew) {
            rest.postForObject(baseUrl + "/create", dto, UtilisateurDTO.class);
        } else {
            rest.put(baseUrl + "/" + dto.getId(), dto);
        }
        table.setItems(fetchUsers());
    }
}
