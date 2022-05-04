package screensControllers;

import employee.Employee;
import employee.ManagEmployees;
import employee.Serveur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import journee.JourneeManager;
import menu.Menu;
import menuBoissons.EnumBoissons;
import menuPlats.EnumPlats;
import menuPlats.PlatsManager;

import java.util.ArrayList;
import java.util.Objects;

public class Serveurs {
    @FXML
    public ListView<String> listPlats;
    @FXML
    public ListView<String> listBoissons;
    @FXML
    public ChoiceBox<String> serveurChoiceBox;
    @FXML
    public Button btnMenuClassique;
    @FXML
    public Button btnMenuCentAns;
    @FXML
    public TreeTableView<Menu> treeTableAffichageMenu;
    @FXML
    public Label textErreur;

    private Serveur currentServeur = null;

    @FXML
    void initialize() {
        initColonnesPanier();
        ajouterServeurs();
    }

    /**
     * Initialise les colonnes du panier
     */
    private void initColonnesPanier() {
        // on crée les colonnes
        TreeTableColumn<Menu, String> platsCol = new TreeTableColumn<>("Plats");
        TreeTableColumn<Menu, String> boissonsCol = new TreeTableColumn<>("Boissons");
        TreeTableColumn<Menu, String> prixCol = new TreeTableColumn<>("Prix");

        // on leur assigne la valeur à laquelle chaque colonne correspond
        platsCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("plat"));
        boissonsCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("boisson"));
        prixCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("prix"));

        // on set la taille de chaque colonne
        platsCol.setPrefWidth(210);
        boissonsCol.setPrefWidth(100);
        prixCol.setPrefWidth(40);

        // on empêche l'utilisateur de trier
        platsCol.setSortable(false);
        boissonsCol.setSortable(false);
        prixCol.setSortable(false);

        // permet un retour à la ligne pour les plats
        Additions.setCellFactory(platsCol);

        // on ajoute les colonnes
        treeTableAffichageMenu.getColumns().addAll(platsCol, boissonsCol, prixCol);
    }

    /**
     * Ajoute les serveurs à la choice box
     */
    private void ajouterServeurs() {
        if (JourneeManager.getInstance().isJourneeEnCours) {
            textErreur.setVisible(false);

            ObservableList<String> listServeurs = FXCollections.observableArrayList();

            // parcours tous les employés
            for (Employee employee : JourneeManager.getInstance().listEmployes)
                // si l'employé est un serveur, on l'ajoute
                if (employee instanceof Serveur)
                    listServeurs.add(employee.getAffichage());

            serveurChoiceBox.setItems(listServeurs);
        } else {
            textErreur.setVisible(true);
            textErreur.setText("Veillez tout d'abord lancer la journée");
        }
    }

    /**
     * Récupère le serveur sélectionné
     */
    public void changeServeurChoiceBox() {
        if (serveurChoiceBox.getValue() != null) {
            for (Employee employee : ManagEmployees.getInstance().getListEmployes()) {
                if (employee.getAffichage().equals(serveurChoiceBox.getValue())) {
                    currentServeur = (Serveur) employee;

                    // si le serveur n'a pas encore réalisé de commande
                    if (JourneeManager.getInstance().getListService().get(employee.getAffichage()) == null) {
                        ajouterMenu();
                        affichePanier();
                        textErreur.setVisible(false);
                    } else {
                        supprimerMenuEtPannier();

                        textErreur.setText("Le serveur actuel a déjà une commande en cours");
                        textErreur.setVisible(true);
                    }
                }
            }
        }
    }

    /**
     * Gère le click que le bouton menu classique
     */
    public void clickMenuClassique() {
        btnMenuClassique.setDisable(true);
        btnMenuCentAns.setDisable(false);
    }

    /**
     * Gère le click sur le bouton menu 100 ans
     */
    public void clickMenuCentAns() {
        btnMenuClassique.setDisable(false);
        btnMenuCentAns.setDisable(true);

        if (currentServeur != null)
            // si le tableua de commandes 100 ans est vide où si le dernier tableau de commande est plein
            if (currentServeur.listCommandes100Ans.size() == 0 ||
                    currentServeur.listCommandes100Ans.get(currentServeur.listCommandes100Ans.size() - 1).size() == 7)
                currentServeur.listCommandes100Ans.add(new ArrayList<>()); // on ajoute un nouveau tableau
    }

    /**
     * Permet d'afficher les plats et boissons
     */
    private void ajouterMenu() {
        ObservableList<String> listStrPlats = FXCollections.observableArrayList();

        for (EnumPlats plat : EnumPlats.values())
            if (PlatsManager.hasEnoughIngredients(plat))
                listStrPlats.add(plat.getName());

        listPlats.setItems(FXCollections.observableArrayList(listStrPlats));


        ObservableList<String> listStrBoissons = FXCollections.observableArrayList();

        for (EnumBoissons boisson : EnumBoissons.values())
            listStrBoissons.add(boisson.getName());

        listBoissons.setItems(FXCollections.observableArrayList(listStrBoissons));
    }

    /**
     * Permet de supprimer le contenu du menu et du panier
     */
    private void supprimerMenuEtPannier() {
        // supprime les menus
        listPlats.setItems(FXCollections.observableArrayList(FXCollections.observableArrayList()));
        listBoissons.setItems(FXCollections.observableArrayList(FXCollections.observableArrayList()));

        // supprime le panier
        TreeItem<Menu> rootItem = new TreeItem<>(new Menu());
        rootItem.setExpanded(true);
        treeTableAffichageMenu.setRoot(rootItem);
        treeTableAffichageMenu.setShowRoot(false); // permet de ne pas afficher le premier parent vide
    }

    /**
     * Gère le click sur une case de la table boisson ou de celle des plats
     */
    public void clickPlatBoisson() {
        // si l'on a cliqué sur un plat ET un boisson
        if (listPlats.getSelectionModel().getSelectedItem() != null && listBoissons.getSelectionModel().getSelectedItem() != null) {
            // on récupère les plats et boissons correspondants
            EnumPlats platCorrespondant = EnumPlats.rechercheParNom(listPlats.getSelectionModel().getSelectedItem());
            EnumBoissons boissonCorrespondant = EnumBoissons.rechercheParNom(listBoissons.getSelectionModel().getSelectedItem());

            if (platCorrespondant != null && boissonCorrespondant != null) {
                // on crée le menu récupéré
                Menu menu = new Menu(platCorrespondant.getName(), boissonCorrespondant.getName(), Integer.toString(platCorrespondant.getPrix() + boissonCorrespondant.getPrix()));

                // on prépare le plat pour enlever les ingrédients
                PlatsManager.preparation(platCorrespondant);

                // cas où l'on est dans un menu classique
                if (btnMenuClassique.isDisable()) currentServeur.listCommandes.add(menu);
                else {
                    // si c'est un menu 100 ans → on supprime le menu du plat et on ajoute le menu à listCommandes100Ans
                    menu.setPrix("");
                    clickMenuCentAns(); // permet de rajouter un tableau si besoin
                    // on rajoute le menu au dernier menu 100 ans non complet
                    for (ArrayList<Menu> tabCommandes : currentServeur.listCommandes100Ans)
                        if (tabCommandes.size() < 7) {
                            tabCommandes.add(menu);
                            break;
                        }
                }

                // on refresh le menu et le panier
                ajouterMenu();
                affichePanier();

                // on désélectionne le plat et la boisson
                listPlats.getSelectionModel().clearSelection();
                listBoissons.getSelectionModel().clearSelection();
            }
        }
    }

    /**
     * Permet de mettre à jour le panier
     */
    public void affichePanier() {
        TreeItem<Menu> rootItem = new TreeItem<>(new Menu());
        rootItem.setExpanded(true);

        // table des menus classiques
        TreeItem<Menu> menuItem = new TreeItem<>(new Menu("Menus classiques"));
        menuItem.setExpanded(true);
        TreeItem<Menu> item;
        if (currentServeur != null) {
            // on ajoute une ligne pour chaque commande classique
            for (Menu menu : currentServeur.listCommandes) {
                item = new TreeItem<>(menu);
                menuItem.getChildren().add(item);
            }
        }
        if (menuItem.getChildren().size() != 0)
            rootItem.getChildren().addAll(menuItem);

        if (currentServeur != null) {
            TreeItem<Menu> menuCentAnsItem;
            // on ajoute une ligne parent Menu 100 ans pour chaque tableau dans listCommandes100Ans
            for (ArrayList<Menu> listMenu : currentServeur.listCommandes100Ans) {
                menuCentAnsItem = new TreeItem<>(new Menu("Menu 100 ans", "", "100"));
                menuCentAnsItem.setExpanded(true);

                // on ajoute une ligne pour chaque menu
                for (Menu menu : listMenu) {
                    item = new TreeItem<>(menu);
                    menuCentAnsItem.getChildren().add(item);
                }

                rootItem.getChildren().add(menuCentAnsItem);
            }
        }

        // on affiche les lignes
        treeTableAffichageMenu.setRoot(rootItem);
        treeTableAffichageMenu.setShowRoot(false); // permet de ne pas afficher le premier parent vide
    }

    /**
     * Supprime un élément du panier au double click
     *
     * @param mouseEvent événement d'entré envoyé par FXML
     */
    public void clickPanier(MouseEvent mouseEvent) {
        // suppression au double click
        if (mouseEvent.getClickCount() >= 2) {
            // on récupère l'élément sélectionné ainsi que son parent
            TreeItem<Menu> itemSelect = treeTableAffichageMenu.getSelectionModel().getSelectedItem();

            try {
                TreeItem<Menu> parent = itemSelect.getParent();

                // supprime le plat pour remettre les ingrédients à disposition
                PlatsManager.suppressionPlat(Objects.requireNonNull(EnumPlats.rechercheParNom(itemSelect.getValue().getPlat())));

                // si c'est un menu, on supprime l'élément du tableau à l'indice sélectionné - 1 ("Menus classiques" occupant le premier index)
                if (parent.getValue().getPlat().equals("Menus classiques"))
                    currentServeur.listCommandes.remove(treeTableAffichageMenu.getSelectionModel().getSelectedIndex() - 1);
                else {
                    // on récupère la liste des types de menus (classiques ou cent ans)
                    ObservableList<TreeItem<Menu>> listMenus = treeTableAffichageMenu.getRoot().getChildren();

                    // on récupère son index
                    int i;
                    for (i = 0; i < listMenus.size(); i++)
                        if (listMenus.get(i) == parent)
                            break;

                    // on accède au tableau index obtenu - 1, car Menus classiques occupe le premier élément
                    currentServeur.listCommandes100Ans.get(i - 1).remove(itemSelect.getValue());
                    if (currentServeur.listCommandes100Ans.get(i - 1).size() == 0)
                        currentServeur.listCommandes100Ans.remove(i - 1);
                }
            } catch (Exception ignored) { // si l'on clique sur "Menus classiques" ou "Menu 100 ans"
            } finally {
                // on refresh le menu et le panier
                ajouterMenu();
                affichePanier();
            }
        }
    }

    /**
     * Enregistre la commande au click sur le bouton enregistrer
     */
    public void clickBtnEnregistrer() {
        if (currentServeur != null && (currentServeur.listCommandes.size() != 0 || currentServeur.listCommandes100Ans.size() != 0)) {
            // si un des menus cents ans ne comporte pas 7 menus
            if (currentServeur.listCommandes100Ans.size() == 0 || currentServeur.listCommandes100Ans.get(currentServeur.listCommandes100Ans.size() - 1).size() == 7) {
                String serveur = currentServeur.getAffichage();
                for (Menu menu : currentServeur.listCommandes)
                    JourneeManager.getInstance().addMenuService(serveur, menu);

                for (ArrayList<Menu> tabMenu100Ans : currentServeur.listCommandes100Ans)
                    for (Menu menu : tabMenu100Ans)
                        JourneeManager.getInstance().addMenuService(serveur, menu);

                currentServeur.listCommandes.clear();
                currentServeur.listCommandes100Ans.clear();

                supprimerMenuEtPannier();
                textErreur.setVisible(true);
                textErreur.setText("Commande enregistré");
            } else {
                textErreur.setVisible(true);
                textErreur.setText("Un menu cent ans doit contenir 7 menus");
            }
        }
    }
}
