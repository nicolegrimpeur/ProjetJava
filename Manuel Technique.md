# Manuel Technique

## Sommaire

- [Introduction](#introduction)

## Dépendances

Ce projet se base sur de nombreuses dépendances :

- [Maven](https://maven.apache.org/) pour la gestion des plugins et la compilation
- [JavaFX](https://openjfx.io/) pour la partie graphique de l'application
- [JSoup](https://jsoup.org/) pour la récupération et la modification d'un fichier HTML
- [Open HTML to PDF](https://github.com/danfickle/openhtmltopdf) pour l'enregistrement d'un fichier HTML en PDF
- [PDFBox](https://pdfbox.apache.org/) d'apache pour la lecture et l'impression de fichiers PDF

## Lancement de l'application

Au lancement de l'application, l'application JavaFX est lancé, entrainant l'ouverture de la page main qui sera la page
principale de l'application. Celle ci possède un AnchorPane dans lequel viendront s'insérer toutes les autres pages de
l'application.

De plus le dossier Additions est supprimé s'il existait avant.

Les différents pages de l'applications se trouvent dans le dossier `src/main/resources/fxml` au format FXML.

Sur la première page, plusieurs boutons sont présents, lançant chacun la page correspondante.

## Structure de l'application

### Ingrédients

Les ingrédients sont stockés sous la forme d'une énumération.
Celle ci contient :

- le nom à afficher de l'ingrédient,
- le stock courant, qui sera modifié tout au long de la journée
- le stock initial, qui stocke les stocks présents au début de la journée
- le stock en fin de journée, qui permet de stocker l'utilisation de la journée

Pour rajouter un nouvel ingrédient, il suffit de le rajouter dans l'énumération.

### Boissons

Les boissons sont stockées sous la forme d'une énumération. Il suffit donc de rajouter la boisson pour qu'elle soit
rajouté dans l'application.

Celles ci sont créées en indiquant leur nom à afficher, leur prix, ainsi que si elles sont alcoolisés ou non.
Le fait de savoir si une boisson est alcoolisé ou non permet d'appliquer une TVA de 20% dessus, sinon, la TVA n'est que
de 10%.

Une méthode `rechercheParNom` permet de retrouver la boisson à partir de son nom d'affichage.

### Plats

De la même manière que les boissons et les ingrédients, les plats sont stockés sous la forme d'énumérations.
Ils sont créés à partir de leur nom à afficher, de leur prix, ainsi que d'une liste d'ingrédients.

Lors de l'initialisation de chaque plat, on parcourt la liste d'ingrédient, afin de remplir une map permettant
d'associer un ingrédient à un nombre, correspondant au nombre de cet ingrédient nécessaire au plat.

Une méthode `rechercheParNom` permet de retrouver le plat à partir de son nom d'affichage.

Une classe PlatsManager permet de gérer les ingrédients lors de l'ajout / suppression d'un plat dans l'application. La
méthode `hasEnoughIngredients` permet de plus de retourner si l'on a suffisamment d'ingrédients pour pouvoir choisir ce
plat dans le menu.

### Employés

Les employés sont répartis en quatre boulots : Barman, Cuisinier, Manager et Serveur.
Ceux ci sont recoupés sous la classe abstraite `Employee` stockant :

- le nom et le prénom de l'employé
- l'affichage de cet employé (en réalité nom + " " + prenom)
- le job et le salaire de l'employé
- le nombre d'item qu'il/elle a vendu (utile afin de voir dans les performances les ventes de chaque salarié)

De plus, pour les serveurs, ceux ci possèdent différentes listes de commande, une pour les menus classiques et une pour
les menus 100 ans. Ces deux listes permettent de stocker les commandes prises dans l'onglet `Prise de commande`.

Une classe `ManagEmployees` permet de stocker chaque employé enregistrez, ainsi que de gérer le nombre de jours
consécutif de chacun.

### Le package files

#### HTML Manager

Cette classe abstraite permet de manipuler des templates HTML, en particulier pour les factures, les additions, ainsi
que la liste
de course.
On retrouve en attributs les chemins des templates ainsi que de sortie des pdf.

Une méthode `createDirectories` permet de créer les dossiers `Additions` et `Factures` dans lesquels seront placés
respectivement les Additions et les Factures.
La lecture des fichiers HTML se fait à l'aide de JSoup, qui permet de modifier un fichier HTML.

Pour les Additions et les Factures, on l'utilise ici afin d'indiquer le numéro de facture pour les factures, le serveur,
la date, la version de l'application, ainsi que tous les menus commandés, en rajoutant des lignes contenant les
informations de chaque menu aux tableaux, à l'aide de la méthode `generateLigne`, permettant de générer une ligne en
fonction des arguments en entrés.

Pour la liste de course, on parcourt juste tous les ingrédients, et on ajoute le nom de l'ingrédient, puis le nombre
d'ingrédients manquant par rapport au début de journée.

#### Impression Manager

Cette classe abstraite ne possède qu'une méthode `imprimerFichier`, permettant d'afficher le menu d'impression pour le
fichier spécifié en argument.

## Déroulement d'une journée

### Gestion des employés

Avant toute chose, il faut ajouter des employés à l'application. Les informations sont récupérées depuis javaFX, et sont
rajoutés au tableau d'employés de la classe `ManagEmployees`.
Le tableau de la liste d'employé affiche tous les employés présents dans ce tableau. En cliquant sur ce tableau, on
récupère l'employé stocké afin de récupérer ses informations pour les modifier.
On peut aussi supprimer l'employé avec le bouton correspondant.
Si on cherche à ajouter un employé possédant les mêmes nom et prénom que l'un des employés, ce dernier sera mis à jour
avec les nouvelles informations, plutôt qu'en créer un nouveau en doublon.

### Réserve

Il faut de plus ajouter les ingrédients présents en réserve. Pour cela, on ajoute tous les ingrédients de l'énumération
dans un tableau. Au click, l'ingrédient est récupéré, permettant de modifier ses stocks à l'aide du spinner.

