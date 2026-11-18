package boutique;

import java.time.LocalDate;

public class Livre extends Article {

    private String isbn;
    private int nbPages;

    public Livre(String description, double prixInitial, int nbExemplaires,
            String isbn, int nbPages) {
        super(description, prixInitial, nbExemplaires);
        this.isbn = isbn;
        this.nbPages = nbPages;
    }

    public int getNbPages() {
        return nbPages;
    }

    @Override
    public String getNumero() {
        return isbn;
    }

    @Override
    public String getType() {
        return "Livre";
    }

    // -50 % au mois d'avril (aussi pour les manuels, qui héritent de Livre)
    @Override
    public double calculerPrix(LocalDate date) {
        double prix = getPrixInitial();
        if (date.getMonthValue() == 4) {
            prix = prix * 0.5;
        }
        return prix;
    }

    @Override
    public String versFichier() {
        return super.versFichier() + " : " + nbPages;
    }

    @Override
    public String toString() {
        return super.toString() + " | " + nbPages + " pages";
    }
}
