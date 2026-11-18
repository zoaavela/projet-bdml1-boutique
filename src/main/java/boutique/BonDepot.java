package boutique;

import java.time.LocalDate;

public class BonDepot {

    private final int NB_MAX_LIGNES = 20;

    private int numero;
    private String telephone;
    private LocalDate dateEmission;
    private LigneDepot[] lignes;
    private int nbLignes;

    public BonDepot(int numero, String telephone, LocalDate dateEmission) {
        this.numero = numero;
        this.telephone = telephone;
        this.dateEmission = dateEmission;
        lignes = new LigneDepot[NB_MAX_LIGNES];
        nbLignes = 0;
    }

    public int getNumero() {
        return numero;
    }

    public String getTelephone() {
        return telephone;
    }

    public LocalDate getDateEmission() {
        return dateEmission;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setDateEmission(LocalDate dateEmission) {
        this.dateEmission = dateEmission;
    }

    // nombre d'articles déposés = nombre de lignes
    public int getNbArticles() {
        return nbLignes;
    }

    public LigneDepot getLigne(int i) {
        return lignes[i];
    }

    // retourne false si le bon est plein ou si la quantité n'est pas correcte
    public boolean ajouterLigne(String numeroArticle, int nbExemplaires) {
        if (nbLignes == NB_MAX_LIGNES || nbExemplaires <= 0) {
            return false;
        }
        lignes[nbLignes] = new LigneDepot(numeroArticle, nbExemplaires);
        nbLignes++;
        return true;
    }

    // true si le bon contient l'article donné
    public boolean contient(String numeroArticle) {
        for (int i = 0; i < nbLignes; i++) {
            if (lignes[i].getNumero().equals(numeroArticle)) {
                return true;
            }
        }
        return false;
    }

    public String versFichier() {
        String ch = numero + System.lineSeparator()
                + telephone + " : " + dateEmission + " : " + nbLignes;
        for (int i = 0; i < nbLignes; i++) {
            ch = ch + System.lineSeparator() + lignes[i].versFichier();
        }
        return ch;
    }

    @Override
    public String toString() {
        String ch = "Bon de dépôt n°" + numero + " du " + dateEmission
                + " | déposant : " + telephone + " | " + nbLignes + " article(s)";
        for (int i = 0; i < nbLignes; i++) {
            ch = ch + System.lineSeparator() + lignes[i];
        }
        return ch;
    }
}
