package com.ofpo.GestionnaireFormation.ui;

import com.ofpo.GestionnaireFormation.model.Utilisateur;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.web.client.RestTemplate;

public class UtilisateurPane extends VBox {
    private final RestTemplate rest = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/utilisateurs";

    private final TableView<Utilisateur> table = new TableView<>();
    private final TextField tfMatricule = new TextField();
    private final TextField tfAvatar = new TextField();
    private final TextField tfNom = new TextField();
    private final TextField tfPrenom = new TextField();
    private final TextField tfMail = new TextField();
    private final TextField tfAdresse = new TextField();
    private final TextField tfCP = new TextField();
    private final TextField tfVille = new TextField();
    private final TextField tfMDP = new TextField();
    private final CheckBox cbActif = new CheckBox("Actif");

    public UtilisateurPane() {
        // colonnes
        TableColumn<Utilisateur, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getId()));

        TableColumn<Utilisateur, String> colMat = new TableColumn<>("Matricule");
        colMat.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getMatricule()));

        TableColumn<Utilisateur, String> colAvatar = new TableColumn<>("Avatar");
        colAvatar.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getAvatar()));

        TableColumn<Utilisateur, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getNom()));

        TableColumn<Utilisateur, String> colPrenom = new TableColumn<>("Prénom");
        colPrenom.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getPrenom()));

        TableColumn<Utilisateur, String> colMail = new TableColumn<>("Mail");
        colMail.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getAdresseMail()));

        TableColumn<Utilisateur, String> colAddr = new TableColumn<>("Adresse");
        colAddr.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getAdressePostal()));

        TableColumn<Utilisateur, String> colCP = new TableColumn<>("Code Postal");
        colCP.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getCodePostal()));

        TableColumn<Utilisateur, String> colVille = new TableColumn<>("Ville");
        colVille.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getVille()));

        TableColumn<Utilisateur, Boolean> colAct = new TableColumn<>("Actif");
        colAct.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getStatut()));

        table.getColumns().addAll(colId, colMat, colAvatar , colNom, colPrenom, colMail, colAddr, colCP, colVille, colAct);
        table.setItems(fetchUsers());

        table.getSelectionModel().selectedItemProperty().addListener((obs,old,nv)-> loadForm(nv));

        // boutons
        Button btnDel = new Button("Supprimer");
        btnDel.setOnAction(e-> {
            Utilisateur u = table.getSelectionModel().getSelectedItem();
            if (u!=null) {
                rest.delete(baseUrl + "/delete/" + u.getId());
                table.setItems(fetchUsers());
            }
        });
        HBox hbBtns = new HBox(5, btnDel);

        // formulaire
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(8);
        form.addRow(0, new Label("Matricule"), tfMatricule);
        form.addRow(1, new Label("Avatar"), tfAvatar);
        form.addRow(2, new Label("Nom"), tfNom);
        form.addRow(3, new Label("Prénom"), tfPrenom);
        form.addRow(4, new Label("Mail"), tfMail);
        form.addRow(5, new Label("Adresse"), tfAdresse);
        form.addRow(6, new Label("Code Postal"), tfCP);
        form.addRow(7, new Label("Ville"), tfVille);
        form.addRow(8, new Label("Mot de Passe"), tfMDP);
        form.add(cbActif, 1, 9);

        Button btnSave = new Button("Enregistrer");
        btnSave.setOnAction(e-> saveOrUpdate());

        this.setSpacing(10);
        this.setPadding(new Insets(15));
        this.getChildren().addAll(
                new Label("Utilisateurs"),
                table,
                hbBtns,
                new Separator(),
                form,
                btnSave
        );
    }

    private ObservableList<Utilisateur> fetchUsers() {
        Utilisateur[] arr = rest.getForObject(baseUrl + "/",
                Utilisateur[].class);
        return FXCollections.observableArrayList(arr);
    }

    private void loadForm(Utilisateur u) {
        if (u==null) {
            tfMatricule.clear(); tfAvatar.clear(); tfNom.clear();
            tfPrenom.clear(); tfMail.clear(); tfAdresse.clear();
            tfCP.clear(); tfVille.clear(); tfMDP.clear();
            cbActif.setSelected(true);
        } else {
            tfMatricule.setText(u.getMatricule());
            tfAvatar.setText(u.getAvatar());
            tfNom.setText(u.getNom());
            tfPrenom.setText(u.getPrenom());
            tfMail.setText(u.getAdresseMail());
            tfAdresse.setText(u.getAdressePostal());
            tfCP.setText(u.getCodePostal());
            tfVille.setText(u.getVille());
            tfMDP.setText(u.getMotDePasse());
            cbActif.setSelected(u.getStatut());
        }
    }

    private void saveOrUpdate() {
        Utilisateur sel = table.getSelectionModel().getSelectedItem();
        Utilisateur dto = (sel==null) ? new Utilisateur() : sel;

        dto.setMatricule(tfMatricule.getText());
        dto.setAvatar(tfAvatar.getText());
        dto.setNom(tfNom.getText());
        dto.setPrenom(tfPrenom.getText());
        dto.setAdresseMail(tfMail.getText());
        dto.setAdressePostal(tfAdresse.getText());
        dto.setCodePostal(tfCP.getText());
        dto.setVille(tfVille.getText());
        dto.setMotDePasse(tfMDP.getText());
        dto.setStatut(cbActif.isSelected());

        if (sel==null) {
            rest.postForObject(baseUrl + "/create", dto, Utilisateur.class);
        } else {
            rest.put(baseUrl + "/update/" + dto.getId(), dto);
        }
        table.setItems(fetchUsers());
    }
}
