# Manuel Technique

## Sommaire

- [Dépendances](#dependances)
- [Lancement de l'application](#lance)
- [Structure de l'application](#structure)
    - [Ingrédients](#ingredients)
    - [Boissons](#boissons)
    - [Plats](#plats)
    - [Menu](#menu)
    - [Employés](#employes)
    - [Le package files](#files)
        - [HTML Manager](#html)
        - [Impression Manager](#impression)
    - [Journée Manager](#journee)
- [Déroulement d'une journée](#deroulement)
    - [Gestion des employés](#gestion)
    - [Réserve](#reserve)
    - [Programmer employés](#programmation)
    - [Prise d'une commande](#commande)
    - [Barmans et Cuisiniers](#barmansEtCuisiniers)
    - [Performances](#performances)
- [FAQ](#FAQ)

## Dépendances <a id="dependances"></a>

Ce projet se base sur de nombreuses dépendances :

- [Maven](https://maven.apache.org/) pour la gestion des plugins et la compilation
- [JavaFX](https://openjfx.io/) pour la partie graphique de l'application
- [JSoup](https://jsoup.org/) pour la récupération et la modification d'un fichier HTML
- [Open HTML to PDF](https://github.com/danfickle/openhtmltopdf) pour l'enregistrement d'un fichier HTML en PDF
- [PDFBox](https://pdfbox.apache.org/) d'apache pour la lecture et l'impression de fichiers PDF

## Lancement de l'application <a id="lancement"></a>

Au lancement de l'application, l'application JavaFX est lancé, entrainant l'ouverture de la page main qui sera la page
principale de l'application. Celle ci possède un AnchorPane dans lequel viendront s'insérer toutes les autres pages de
l'application.

De plus le dossier Additions est supprimé s'il existait avant.

Les différents pages de l'applications se trouvent dans le dossier `src/main/resources/fxml` au format FXML.

Sur la première page, plusieurs boutons sont présents, lançant chacun la page correspondante.

## Structure de l'application<a id="structure"></a>

### Ingrédients <a id="ingredients"></a>

Les ingrédients sont stockés sous la forme d'une énumération.
Celle ci contient :

- le nom à afficher de l'ingrédient,
- le stock courant, qui sera modifié tout au long de la journée
- le stock initial, qui stocke les stocks présents au début de la journée
- le stock en fin de journée, qui permet de stocker l'utilisation de la journée

Pour rajouter un nouvel ingrédient, il suffit de le rajouter dans l'énumération.

### Boissons <a id="boissons"></a>

Les boissons sont stockées sous la forme d'une énumération. Il suffit donc de rajouter la boisson pour qu'elle soit
rajouté dans l'application.

Celles ci sont créées en indiquant leur nom à afficher, leur prix, ainsi que si elles sont alcoolisés ou non.
Le fait de savoir si une boisson est alcoolisé ou non permet d'appliquer une TVA de 20% dessus, sinon, la TVA n'est que
de 10%.

Une méthode `rechercheParNom` permet de retrouver la boisson à partir de son nom d'affichage.

### Plats <a id="plats"></a>

De la même manière que les boissons et les ingrédients, les plats sont stockés sous la forme d'énumérations.
Ils sont créés à partir de leur nom à afficher, de leur prix, ainsi que d'une liste d'ingrédients.

Lors de l'initialisation de chaque plat, on parcourt la liste d'ingrédient, afin de remplir une map permettant
d'associer un ingrédient à un nombre, correspondant au nombre de cet ingrédient nécessaire au plat.

Une méthode `rechercheParNom` permet de retrouver le plat à partir de son nom d'affichage.

Une classe PlatsManager permet de gérer les ingrédients lors de l'ajout / suppression d'un plat dans l'application. La
méthode `hasEnoughIngredients` permet de plus de retourner si l'on a suffisamment d'ingrédients pour pouvoir choisir ce
plat dans le menu.

### Menu <a id="menu"></a>

La classe `Menu` permet de stocker toutes les informations liées à un menu comme le plat, la boisson, le prix, ainsi que
les status du plat et de la boisson.

### Employés <a id="employes"></a>

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

### Le package files <a id="files"></a>

#### HTML Manager <a id="html"></a>

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

#### Impression Manager <a id="impression"></a>

Cette classe abstraite ne possède qu'une méthode `imprimerFichier`, permettant d'afficher le menu d'impression pour le
fichier spécifié en argument.

### Journée Manager <a id="journee"></a>

Cette classe contient tous les éléments liés à une journée, notamment :

- la liste des employés présents sur la journée
- un booléen permettant de savoir si la journée est lancé ou pas
- une map comprenant une liste des menus en cours de préparation pour un serveur en particulier
- une map comprenant la liste des menus vendus par serveur
- une map permettant de stocker si une addition a été payé ou pas

Au niveau de ses méthodes principales, on retrouve :

- `debutJournee` permettant de lancer la journée (suppression des tableaux liés à la journée précédente,
  initialisation des stocks initiaux de chaque ingrédient et suppression des additions de la journée précédente)
- `delete` permettant de supprimer un dossier
- `finJournee` permettant d'arrêter la journée
- `nextStatusPlat` et `nextStatusBoisson` permettant de passer les éléments d'un menu à l'état suivant
- `platTermine` et `boissonTermine` permettant d'indiquer que les plats / boissons ont terminé d'être préparé
- `ajoutMenusServeur` permettant de gérer le service d'une table en enregistrant la vente ainsi qu'en générant
  l'addition et la facture
- `calculTVA` permet de calculer pour une liste de Menu, le prix total, le prix hors taxe, ainsi que le cout de la TVA à
  10% et celle à 20%
- `resetVentes` permettant de remettre à 0 les différents tableaux de la journée

## Déroulement d'une journée <a id="deroulement"></a>

### Gestion des employés <a id="gestion"></a>

Avant toute chose, il faut ajouter des employés à l'application. Les informations sont récupérées depuis javaFX, et sont
rajoutés au tableau d'employés de la classe `ManagEmployees`.
Le tableau de la liste d'employé affiche tous les employés présents dans ce tableau. En cliquant sur ce tableau, on
récupère l'employé stocké afin de récupérer ses informations pour les modifier.
On peut aussi supprimer l'employé avec le bouton correspondant.
Si on cherche à ajouter un employé possédant les mêmes nom et prénom que l'un des employés, ce dernier sera mis à jour
avec les nouvelles informations, plutôt qu'en créer un nouveau en doublon.

### Réserve <a id="reserve"></a>

Il faut de plus ajouter les ingrédients présents en réserve. Pour cela, on ajoute tous les ingrédients de l'énumération
dans un tableau. Au click, l'ingrédient est récupéré, permettant de modifier ses stocks à l'aide du spinner.

Un bouton permettant d'imprimer la liste de courses récupère le fichier HTML template, le remplit, l'enregistre dans le
dossier resources, puis lance l'impression à l'aide de la méthode `imprimerFichier` dans la classe `ImpressionManager`.

### Programmer employés <a id="programmation"></a>

Pour la programmation des employés sur la journée, on affiche deux tableaux, le premier avec la liste de tous les
employés présent dans la liste d'employés de `ManagEmployee` moins les employés ayant déjà réalisé 2 jours consécutifs (
sauf pour les managers) et les employés configurer comme participant à la journée. Le deuxième tableau n'affiche lui que
les employés présents pour la journée.

On gère le click sur ces deux tables afin de supprimer l'élément sélectionné en double-cliquant. Deux boutons sont sinon
présents afin d'ajouter ou de supprimer un employé.

Le bouton `Lancer la journée` reste désactivé tant que les conditions de lancement ne sont pas réunis, notamment la
présence d'au moins 4 cuisiniers, 2 serveurs, 1 barman, et de 1 manager.

Une fois ces conditions remplies, on peut lancer la journée en cliquant sur le bouton correspondant. Les tables ainsi
que le bouton pour lancer la journée sont ensuite désactivés, et la classe `JourneeManager` est remise à 0.

### Prise d'une commande <a id="commande"></a>

Lors de l'arrivée sur la page de commande, on initialise les différentes colonnes du panier, ainsi que la choiceBox
permettant de sélectionner le serveur qui effectue la commande.

Une fois le serveur sélectionné, on affiche le menu, ainsi que le panier qui lui est affilié (de base vide, mais permet
de sortir puis de revenir sur la page sans perdre la commande).

Au click sur la liste de plat ou de boisson, on vérifie si une boisson et un plat sont sélectionnés, avant de les
enregistrer dans le tableau lié au serveur dans la classe `Serveur`, soit du menu classique si le
bouton `Menu classique` est sélectionné, soit du menu 100 ans si le bouton correspondant est sélectionné, après quoi on
peut l'afficher dans le panier.

Au click sur un élément du panier, on vérifie si on a double-cliqué dessus, afin de supprimer l'élément du panier.

Au click sur le bouton enregistrer, on passe la commande dans le service de `JourneeManager` afin que les Barmans et
Cuisiniers la récupèrent.

### Barmans et Cuisiniers <a id="barmansEtCuisniers"></a>

La gestion des barmans et cuisiniers est la même. L'affichage est ainsi identique et regroupé sous un seul contrôleur.
La différence se situe dans l'appel de cette page, en fonction du click sur le bouton `Barmans` ou `Serveurs`. On
affecte pour cela la variable typeItem à `boisson` ou à `plat` directement depuis le lancement de la page.

On initialise ensuite la choiceBox permettant de sélectionner l'employé qui va réaliser les commandes, ainsi que les
colonnes du tableau d'affichage des éléments à préparer, avant d'afficher directement les éléments à préparer.

Une fois qu'un employé est sélectionné, il peut se déplacer dans le tableau des items à préparer, puis cliquer
sur `Entrer`, ou sur le Bouton `Etat Suivant` pour passer l'item à l'état suivant.
Pour le click sur `Entrer`, on lance la méthode `keyReleasedOnTable` afin de vérifier si la touche pressée est bien la
touche entrer. On vérifie ensuite que l'item sélectionné peut être passé à l'état suivant, ou servi si toute la commande
est prête à être servi.

Au click sur le bouton servi (ou en appuyant sur la touche `Entrer`) sur la commande d'un serveur, on lance la
méthode `boissonTermine` ou `platTermine` de `JourneeManager` vu précédemment, afin de marquer la commande comme
réalisé, pour enregistrement, création de l'addition, de la facture, et stockage pour les performances.

### Performances <a id="performances"></a>

La page performance permet d'afficher toutes les statistiques liées à une journée.

Pour cela, on commence par calculer le nombre de plats et boissons vendus et afficher le résultat dans des labels, ainsi
que le chiffre d'affaires avec et sans taxes, avant d'initialiser les différentes colonnes de chaque tableau, afin de
configurer les attributs qu'ils afficheront, puis d'afficher ces valeurs.

Pour les performances des Serveurs, Barmans et Cuisiniers, on regarde l'attribut `nbItemsVendus` de la classe `Employee`
.
Pour les ingrédients, on passe par la classe `AffichagePerformance` pour afficher l'ingrédient, ainsi que le nombre
d'ingrédients manquants par rapport au début de la journée.
Enfin, pour les plats et les boissons, on parcourt la liste de menus vendus afin de calculer le nombre de chaque plat /
boisson vendus, puis on les affiche avec la classe `AffichagePerformance`.

## FAQ <a id="FAQ"></a>

### Je suis développeur, comment exécuter le projet ?

Tout d'abord vérifiez que vous avez Maven d'installé sur votre ordinateur (ou alors présents dans votre IDE).

Pour run le projet, faites `mvn clean javafx:run` dans le projet.

Pour compiler le projet sous jar, faites `mvn clean install`. Le jar de sortie se trouvera ensuite dans le
dossier `target`. Deux fichiers jar se trouveront dedans, l'un avec les dépendances du projet et l'autre sans. Pensez à
prendre celle avec les dépendances pour exécuter le projet avec le fichier CMD `launchApplication - with dependencies.cmd`.
