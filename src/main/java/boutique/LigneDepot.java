package boutique;

// Une ligne d'un bon de dépôt
public class LigneDepot {

    private String numero; // ISBN ou ISSN
    private int nbExemplaires;

    public LigneDepot(String numero, int nbExemplaires) {
        this.numero = numero;
        this.nbExemplaires = nbExemplaires;
    }

    public String getNumero() {
        return numero;
    }

    public int getNbExemplaires() {
        return nbExemplaires;
    }

    // nombre d'exemplaires : référence
    public String versFichier() {
        return nbExemplaires + " : " + numero;
    }

    @Override
    public String toString() {
        return "   - " + numero + " x " + nbExemplaires;
    }
}
