package boutique;

import java.time.LocalDate;

// Classe mère de tous les articles
public class Article {

    private String description;
    private double prixInitial;
    private int nbExemplaires;

    public Article(String description, double prixInitial, int nbExemplaires) {
        this.description = description;
        this.prixInitial = prixInitial;
        this.nbExemplaires = nbExemplaires;
    }

    public String getDescription() {
        return description;
    }

    public double getPrixInitial() {
        return prixInitial;
    }

    public int getNbExemplaires() {
        return nbExemplaires;
    }

    // redéfinie dans Livre et Magazine
    public String getNumero() {
        return "";
    }

    // redéfinie dans chaque sous-classe
    public String getType() {
        return "Article";
    }

    // true si cet article doit être placé après l'autre dans le tableau
    public boolean placerApres(Article autre) {
        return this.getNumero().compareTo(autre.getNumero()) > 0;
    }

    // retourne false si la quantité n'est pas correcte
    public boolean ajouter(int quantite) {
        if (quantite <= 0) {
            return false;
        }
        nbExemplaires = nbExemplaires + quantite;
        return true;
    }

    // retourne false si la quantité n'est pas correcte ou si le stock ne suffit pas
    public boolean retirer(int quantite) {
        if (quantite <= 0 || quantite > nbExemplaires) {
            return false;
        }
        nbExemplaires = nbExemplaires - quantite;
        return true;
    }

    // prix à une date donnée, sans réduction pour un article simple
    public double calculerPrix(LocalDate date) {
        return prixInitial;
    }

    // prix à la date du jour
    public double calculerPrix() {
        return calculerPrix(LocalDate.now());
    }

    // ligne 1 : numéro, ligne 2 : description : prix : nombre d'exemplaires
    public String versFichier() {
        return getNumero() + System.lineSeparator()
                + description + " : " + prixInitial + " : " + nbExemplaires;
    }

    @Override
    public String toString() {
        return "[" + getType() + "] " + getNumero() + " | " + description
                + " | prix initial : " + prixInitial + " euros | exemplaires : " + nbExemplaires;
    }
}
