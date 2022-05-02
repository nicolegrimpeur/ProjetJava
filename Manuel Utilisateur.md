# Manuel Utilisateur

## Sommaire

- [Introduction](#introduction)
    - [Lancer l'application](#lancer)
    - [Interface](#interface)
- [Monitoring](#monitoring)
    - [Gestion des employés](#gestion)
    - [Réserves](#reserves)
    - [Programmer employés](#programmation)
    - [Performances](#performances)
- [Prendre une commande](#commande)
- [Barmans](#barmans)
- [Cuisiniers](#cuisiniers)
- [Additions](#additions)

## Introduction <a id="introduction"></a>

### Lancer l'application <a id="lancer"></a>

Pour lancer l'application, il suffit de cliquer sur le script de commande "launchApplication.cmd". L'application s'
ouvrira ensuite toute seule.

### Interface <a id="interface"></a>

L'interface est répartie en plusieurs parties :

- La **barre de menu**, à partir de laquelle on peut fermer l'application (Cliquer sur `File` puis `Close`) ou obtenir
  la version de l'application (`Help` puis `A propos`).
- Les **boutons de navigation** permettant d'accéder à chacun des écrans de gestion. Le bouton retour ne s'affiche que
  lorsque l'utilisateur ne se trouve pas sur la page d'accueil.

- L'**affichage de l'écran** sélectionné en cliquant sur un des boutons de navigation.

## Monitoring <a id="monitoring"></a>

Le bouton monitoring permet d'afficher d'autres boutons de navigation qui sont les suivants :

- Performances
- Gestion employés
- Réserves
- Programmer employés

Il suffit de cliquer sur l'un d'entre eux pour accéder à l'écran voulu.

> Il est nécessaire de **planifier la journée** au préalable avant de pouvoir utiliser le reste des fonctionnalités de
> l'application.

### Gestion des employés <a id="gestion"></a>

L'écran de gestion des employés permet d'ajouter ou de supprimer un employé dont le contrat serait arrivé à terme.

Pour **ajouter un employé**, entrez son nom, son prénom, son salaire et sélectionnez son emploi dans les champs
correspondants. Puis cliquez sur le bouton `Ajouter employé`. Il apparaîtra alors dans le tableau se situant à droite.

Pour **supprimer un employé**, cliquez sur l'employé à supprimer dans le tableau puis cliquez sur le
bouton `Supprimer employé`. Il sera alors supprimé du tableau.

Pour **modifier un employé**, clique sur l'employé à modifier dans le tableau.
Ses informations s'afficheront sur le côté, vous pouvez par ce biais modifier directement le job ou le salaire de
l'employé.

### Réserves <a id="reserves"></a>

L'écran de réserve permet de voir les ingrédients en stock mais aussi de modifier ceux-ci et d'imprimer la liste des
ingrédients à ajouter afin de retrouver le stock initial au début de la journée.

Pour **ajouter des ingrédients**, cliquez sur la ligne de l'ingrédient dont vous souhaitez modifier le stock dans le
tableau.
Le nombre d'ingrédient en stock devrait alors apparaître à la droite du tableau. Vous pouvez alors le modifier.

Pour **imprimer la liste de courses**, cliquez sur le bouton `Imprimer liste de course` lorsque vous avez modifié les
stocks à votre guise.
La liste de courses contiendra un tableau avec le nombre d'ingrédients manquant dans votre réserve.

### Programmer employés <a id="programmation"></a>

L'écran de programmation des employés permet de planifier la journée et d'inscrire lesquels seront présents.

> - Pour commencer une journée il faut **au moins** 1 manager, 1 barman, 2 serveurs et 4 cuisiniers.
> - Un employé (autre que manager) ne peut **pas travailler 3 jours de suite**.
> - Le bouton pour lancer la journée sera grisé tant que ces conditions ne seront pas respectées.
> - Une fois que la journée est lancée il n'est plus possible de modifier les listes.

Pour **ajouter un employé pour la journée** double-cliquez sur le nom de l'employé souhaité dans la Liste d'employés.
Vous pouvez également sélectionner l'employé en cliquant une fois sur son nom puis en cliquant sur le bouton `Ajouter`.

Pour **supprimer un employé pour la journée**, procédez de la même façon à partir de la liste des employés de la journée
et en cliquant sur le bouton `Supprimer`.

Pour **lancer une journée** : une fois que la liste est complète, lancez la journée en cliquant sur le
bouton `Lancer le début de la journée`.

Pour **terminer la journée** : cliquer sur le bouton `Terminer la journée`. Il sera alors possible de planifier la
prochaine journée.

> Il n'est pas possible de terminer une journée lorsque des commandes sont encore en cours.

### Performances <a id="performances"></a>

L'écran de performances permet de consulter les données suivantes sur la journée :

- Le chiffre d'affaires total de la journée
- Le nombre de plats vendus
- Le nombre de boissons vendues
- Le nombre de boissons préparées par chaque barman
- Le nombre de menus servis par chaque serveur
- Le nombre de plats réalisés par chaque cuisinier
- Le nombre d'ingrédients utilisés et lesquels
- Le détail des plats et des boissons vendus

## Prendre une commande <a id="commande"></a>

Pour prendre la commande:

- **Sélectionnez un serveur**. La liste des plats et des boissons apparaîtra alors.
- Cliquer sur un plat et une boisson pour l'**ajouter à la commande**.
- Pour **supprimer un menu** de la commande, double-cliquez sur le menu à supprimer dans le tableau de commande.
- Pour prendre une commande du menu "Cent ans" (7 plats et 7 boissons pour 100€), cliquez sur le
  bouton `Commande 100 ans`.
- Pour repasser sur une commande classique, cliquez sur le bouton `Commande classique`.
- Pour **valider la prise de commande** cliquez sur le bouton `Submit`.

## Barmans <a id="barmans"></a>

Pour préparer les boissons :

- Sélectionner un barman
- Cliquez sur la boisson à préparer puis cliquez sur `Etat suivant`. La boisson passera alors au statut suivant "En
  cours". Passez encore à l'état suivant et la boisson sera au statut "A servir".
- Lorsque toutes les boissons sont au statut "A servir", le statut du serveur en charge de la commande passe au statut "
  En attente".

Servir la commande :

- Cliquer sur le nom du serveur en charge de la commande puis sur le bouton `Servi`.

> La commande ne peut être servie que si tous les plats et toutes les boissons de la commande sont prêts, lorsque le
> statut du serveur est "A servir".

## Cuisiniers <a id="cuisiniers"></a>

Pour préparer les plats le processus est similaire à celui de la préparation des boissons :

- Sélectionner un cuisinier
- Cliquez sur le plat à préparer puis cliquez sur `Etat suivant`. Le plat passera alors au statut suivant "En cours".
  Passez encore à l'état suivant et le plat sera au statut "A servir".
- Lorsque tous les plats sont au statut "A servir", le statut du serveur en charge de la commande passe au statut "En
  attente". Pour pouvoir servir la commande il faut que les boissons aient été préparées aussi.

Servir la commande :

- Cliquer sur le nom du serveur en charge de la commande puis sur le bouton `Servi`.

> La commande ne peut être servie que si tous les plats et toutes les boissons de la commande sont prêts, lorsque le
> statut du serveur est "À servir".

Une fois la commande servie, une facture est générée dans le dossier "Factures", ainsi qu'une addition générée dans le
dossier "Additions". Cette dernière peut être imprimé depuis l'écran Addition ci après.

## Additions <a id="additions"></a>

L'écran des additions permet l'accès à une liste d'additions qu'il est possible d'imprimer individuellement.
Pour le faire, cliquez sur l'addition voulue puis sur le bouton `Imprimer`.

Sur l'addition figurent :

- la date et l'heure de la commande
- le serveur qui s'est occupé de la commande.
- les menus séparément (Classique ou Cent ans) ainsi que leurs plats, boissons et prix.
- Le prix total de la commande.