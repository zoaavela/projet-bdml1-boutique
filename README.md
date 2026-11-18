# BoutiqueOccasion

Gestion de stock d'une boutique d'articles culturels d'occasion (TP3 et TP4, Conception et Programmation Objet, 2026/2027).

## Ouvrir dans NetBeans

1. File > Open Project, choisir le dossier `BoutiqueOccasion` (projet Maven).
2. Lancer le programme : clic droit sur `Main.java` > Run File.

JDK 17 ou plus récent. Les fichiers `articles.txt` et `depots.txt` sont lus et écrits à la racine du projet.
Garder une copie des deux fichiers d'origine pour remettre la démo à zéro.
Saisir les numéros sans espaces et les prix avec un point (6.5).

## Classes (paquetage `boutique`)

| Classe | Rôle |
| --- | --- |
| `Article` | Classe mère : description, prix initial, nombre d'exemplaires |
| `Livre` | ISBN, nombre de pages, réduction d'avril |
| `Manuel` | Hérite de `Livre` : matière, niveau scolaire |
| `Magazine` | ISSN, périodicité, date de publication, réductions selon l'ancienneté |
| `LigneDepot` | Numéro d'article et nombre d'exemplaires déposés |
| `BonDepot` | Bon numéroté et daté, tableau de lignes |
| `Etablissement` | Tableaux d'articles et de bons, toutes les règles de gestion, fichiers |
| `Main` | Menu console, saisies, chargement et sauvegarde |

Le diagramme de classes est dans `docs/diagramme_classes.png` (source Graphviz : `docs/diagramme_classes.dot`).

Diagrammes : `docs/diagramme_classes.png` et `docs/diagramme_depot.png`.
