package boutique;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        Etablissement etab = new Etablissement("Boutique Culture d'Occasion");

        // chargement des fichiers (au premier lancement ils peuvent ne pas exister)
        try {
            etab.depuisFichierArticles("articles.txt");
            etab.depuisFichierDepots("depots.txt");
        } catch (IOException e) {
            System.err.println("Fichiers introuvables : la boutique démarre vide.");
        }
        System.out.println(etab.getNbArticles() + " article(s) et " + etab.getNbBons() + " bon(s) chargés.");

        int choix;
        do {
            System.out.println();
            System.out.println("1. Afficher la boutique");
            System.out.println("2. Lister les articles (prix du jour)");
            System.out.println("3. Rechercher un article");
            System.out.println("4. Ajouter un article");
            System.out.println("5. Enregistrer un dépôt");
            System.out.println("6. Augmenter le stock d'un article");
            System.out.println("7. Retirer des exemplaires (vente)");
            System.out.println("8. Bons de dépôt d'un client");
            System.out.println("9. Bons de dépôt d'un article sur une période");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = Integer.valueOf(sc.nextLine());

            switch (choix) {
                case 1 -> System.out.println(etab);
                case 2 -> System.out.println(etab.lister());
                case 3 -> rechercher(etab);
                case 4 -> nouvelArticle(etab);
                case 5 -> nouveauDepot(etab);
                case 6 -> modifierStock(etab, true);
                case 7 -> modifierStock(etab, false);
                case 8 -> {
                    System.out.print("Téléphone : ");
                    System.out.println(etab.lister(sc.nextLine()));
                }
                case 9 -> bonsParPeriode(etab);
                case 0 -> System.out.println("Au revoir.");
                default -> System.err.println("Choix inconnu.");
            }
        } while (choix != 0);

        // sauvegarde avant de quitter
        etab.versFichierArticles("articles.txt");
        etab.versFichierDepots("depots.txt");
    }

    static void rechercher(Etablissement etab) {
        System.out.print("Numéro ISBN ou ISSN : ");
        Article a = etab.rechercher(sc.nextLine());
        if (a == null) {
            System.err.println("Article introuvable.");
        } else {
            System.out.println(a);
        }
    }

    static void nouvelArticle(Etablissement etab) {
        System.out.print("Numéro ISBN (13 chiffres) ou ISSN (8 chiffres) : ");
        String numero = sc.nextLine();
        System.out.print("Nombre d'exemplaires : ");
        int nb = Integer.valueOf(sc.nextLine());
        if (saisirArticle(etab, numero, nb)) {
            System.out.println("Article ajouté.");
        } else {
            System.err.println("Ajout refusé (numéro incorrect, déjà utilisé ou boutique pleine).");
        }
    }

    // saisit les informations d'un article et l'ajoute à la boutique
    static boolean saisirArticle(Etablissement etab, String numero, int nb) {
        System.out.print("Description : ");
        String desc = sc.nextLine();
        System.out.print("Prix initial (ex : 6.5) : ");
        double prix = Double.valueOf(sc.nextLine());

        if (numero.length() == 8) {
            System.out.print("Périodicité (hebdomadaire, mensuel ou trimestriel) : ");
            String periodicite = sc.nextLine();
            LocalDate date = saisirDate("Date de publication");
            return etab.ajouter(desc, prix, nb, numero, periodicite, date);
        }

        System.out.print("Nombre de pages : ");
        int pages = Integer.valueOf(sc.nextLine());
        System.out.print("Est-ce un manuel scolaire ? (o/n) : ");
        if (sc.nextLine().equals("o")) {
            System.out.print("Matière : ");
            String matiere = sc.nextLine();
            System.out.print("Niveau : ");
            String niveau = sc.nextLine();
            return etab.ajouter(desc, prix, nb, numero, pages, matiere, niveau);
        }
        return etab.ajouter(desc, prix, nb, numero, pages);
    }

    static void nouveauDepot(Etablissement etab) {
        System.out.print("Téléphone du déposant : ");
        BonDepot bon = new BonDepot(0, sc.nextLine(), LocalDate.now());

        System.out.print("Nombre d'articles différents déposés : ");
        int n = Integer.valueOf(sc.nextLine());
        for (int i = 0; i < n; i++) {
            System.out.print("Numéro ISBN ou ISSN : ");
            String numero = sc.nextLine();
            System.out.print("Nombre d'exemplaires : ");
            int qte = Integer.valueOf(sc.nextLine());

            boolean ok = true;
            if (etab.rechercher(numero) == null) {
                // article inconnu : on le crée avec 0 exemplaire, le bon ajoutera la quantité
                System.out.println("Nouvel article.");
                ok = saisirArticle(etab, numero, 0);
            }
            if (ok && bon.ajouterLigne(numero, qte)) {
                System.out.println("Ligne ajoutée.");
            } else {
                System.err.println("Ligne refusée.");
            }
        }

        if (etab.ajouter(bon)) {
            System.out.println(bon);
        } else {
            System.err.println("Bon non enregistré.");
        }
    }

    // augmenter = true pour ajouter, false pour retirer
    static void modifierStock(Etablissement etab, boolean augmenter) {
        System.out.print("Numéro ISBN ou ISSN : ");
        String numero = sc.nextLine();
        System.out.print("Quantité : ");
        int qte = Integer.valueOf(sc.nextLine());
        boolean ok;
        if (augmenter) {
            ok = etab.ajouter(numero, qte);
        } else {
            ok = etab.retirer(numero, qte);
        }
        if (ok) {
            System.out.println("Stock mis à jour : " + etab.rechercher(numero));
        } else {
            System.err.println("Opération refusée.");
        }
    }

    static void bonsParPeriode(Etablissement etab) {
        System.out.print("Numéro ISBN ou ISSN : ");
        String numero = sc.nextLine();
        LocalDate debut = saisirDate("Début de la période");
        LocalDate fin = saisirDate("Fin de la période");
        System.out.println(etab.lister(numero, debut, fin));
    }

    static LocalDate saisirDate(String message) {
        System.out.println(message);
        System.out.print("  année : ");
        int a = Integer.valueOf(sc.nextLine());
        System.out.print("  mois : ");
        int m = Integer.valueOf(sc.nextLine());
        System.out.print("  jour : ");
        int j = Integer.valueOf(sc.nextLine());
        return LocalDate.of(a, m, j);
    }
}
