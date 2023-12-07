## API d'un portail de mise en relation entre locataires et propriétaires

Cette application est l'API d'un portail de mise en relation entre les futurs locataires et les propriétaires pour de la location saisonnière sur la côte basque.

## Installation

Pré-requis :
- il est nécessaire d'avoir une base mysql intitulée "rentals", et d'un user avec les droits sur la base.
- pour la gestion des images, il faut avoir un dossier /Users/a240707/Temp/StorageRentalPics créé en local et un Simple Web Server pointant sur ce dossier, lancé sur le port 8081

1. Clonez le dépôt git avec la commande suivante dans un terminal : `git clone https://github.com/cedh44/Projet3APIRentals`
2. Installation des dépendances et compilation : placez vous dans le répertoire racine du projet et tapez `mvn clean install`
3. Appliquez le script sql suivant, à la racine du projet, à l'aide de la commande suivante : mysql -u root -p rentals < src/main/resources/sql/script.sql
4. Modifier le fichier application.properties pour ajouter les variables d'environnement aux propriétés spring.datasource.username et spring.datasource.password (cela concerne le couplet user/mot de passe de la base mysql)

## Lancer l'application

Ce projet a été développé sous IntelliJ IDEA.
Pour lancer l'application, cliquez sur la classe RentalsApplication.java et cliquez sur Run.

Une autre façon de lancer l'application est de taper la commande suivante dans un terminal, à la racine du projet  : mvn spring-boot:run

L'API est accessible à l'url suivante : http://localhost:8080/

## Documentation - Swagger

La documentation de l'application, sous forme de swagger, est disponible à l'url suivante depuis un navigateur : http://localhost:8080/swagger-ui/

## Technologies
- Spring Boot
- Java 17
- Maven
- MySql
- Git

## Licence

Développé dans le cadre du projet 3 de la formation Développeur Full-Stack - Java et Angular (OpenClassrooms) : https://openclassrooms.com/fr/paths/533/projects/1303/assignment
