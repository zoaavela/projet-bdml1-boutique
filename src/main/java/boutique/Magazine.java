package boutique;

import java.time.LocalDate;

public class Magazine extends Article {

    private String issn;
    private String periodicite; // hebdomadaire, mensuel ou trimestriel
    private LocalDate datePublication;

    public Magazine(String description, double prixInitial, int nbExemplaires,
            String issn, String periodicite, LocalDate datePublication) {
        super(description, prixInitial, nbExemplaires);
        this.issn = issn;
        this.periodicite = periodicite;
        this.datePublication = datePublication;
    }

    public String getPeriodicite() {
        return periodicite;
    }

    public LocalDate getDatePublication() {
        return datePublication;
    }

    @Override
    public String getNumero() {
        return issn;
    }

    @Override
    public String getType() {
        return "Magazine";
    }

    // -50 % après 2 périodes, -75 % après 4 périodes
    // (une période = 1 semaine, 1 mois ou 1 trimestre de 3 mois)
    @Override
    public double calculerPrix(LocalDate date) {
        double prix = getPrixInitial();
        LocalDate limite50;
        LocalDate limite75;

        if (periodicite.equals("hebdomadaire")) {
            limite50 = datePublication.plusWeeks(2);
            limite75 = datePublication.plusWeeks(4);
        } else if (periodicite.equals("mensuel")) {
            limite50 = datePublication.plusMonths(2);
            limite75 = datePublication.plusMonths(4);
        } else {
            limite50 = datePublication.plusMonths(6);
            limite75 = datePublication.plusMonths(12);
        }

        if (date.isAfter(limite75)) {
            prix = prix * 0.25;
        } else if (date.isAfter(limite50)) {
            prix = prix * 0.5;
        }
        return prix;
    }

    @Override
    public String versFichier() {
        return super.versFichier() + " : " + periodicite + " : " + datePublication;
    }

    @Override
    public String toString() {
        return super.toString() + " | " + periodicite + " | publié le " + datePublication;
    }
}
