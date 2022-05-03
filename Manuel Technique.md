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