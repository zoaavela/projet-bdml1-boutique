# Boutique d'articles culturels d'occasion

TP Conception et Programmation Objet, EFREI, 2026/2027.

Programme Java en console qui gère le stock d'une boutique de livres, manuels scolaires et magazines d'occasion, ainsi que les bons de dépôt des donateurs.

## Contenu

| Classe | Rôle |
| --- | --- |
| `Article` | Classe abstraite : description, prix initial, nombre d'exemplaires |
| `Livre` | ISBN 13 chiffres, nombre de pages, -50 % en avril |
| `Manuel` | Livre avec matière et niveau scolaire |
| `Magazine` | ISSN 8 chiffres, périodicité, date de publication, remise selon l'ancienneté |
| `Periodicite` | Énumération : hebdomadaire, mensuel, trimestriel |
| `LigneDepot` | Une référence (ISBN ou ISSN) et une quantité déposée |
| `BonDepot` | Téléphone, date, lignes de dépôt, numéro attribué à l'enregistrement |
| `Etablissement` | Articles triés par numéro, bons triés par date |
| `Main` | Tests de la première partie |

## Avancement

- [x] TP3 : classes, gestion des articles, bons de dépôt
- [ ] TP4 : méthodes `lister`, sauvegarde et chargement des fichiers texte, menu

## Lancer le projet

Prérequis : JDK 17 ou plus.

Avec NetBeans : ouvrir le dossier (projet Maven), puis Run.

En ligne de commande :

```bash
mvn compile exec:java
```

Sans Maven :

```bash
javac -d out src/main/java/fr/efrei/boutique/*.java
java -cp out fr.efrei.boutique.Main
```

## Arborescence

```
src/main/java/fr/efrei/boutique/   sources Java
pom.xml                            configuration Maven
```

## Équipe

- Nom Prénom
- Nom Prénom
- Nom Prénom
