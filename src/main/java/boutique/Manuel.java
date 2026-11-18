package boutique;

// Un manuel est un livre avec une matière et un niveau
public class Manuel extends Livre {

    private String matiere;
    private String niveau;

    public Manuel(String description, double prixInitial, int nbExemplaires,
            String isbn, int nbPages, String matiere, String niveau) {
        super(description, prixInitial, nbExemplaires, isbn, nbPages);
        this.matiere = matiere;
        this.niveau = niveau;
    }

    public String getMatiere() {
        return matiere;
    }

    public String getNiveau() {
        return niveau;
    }

    @Override
    public String getType() {
        return "Manuel";
    }

    @Override
    public String versFichier() {
        return super.versFichier() + " : " + matiere + " : " + niveau;
    }

    @Override
    public String toString() {
        return super.toString() + " | " + matiere + " | " + niveau;
    }
}
