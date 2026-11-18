package boutique;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class Etablissement {

    private final int NB_MAX_ARTICLES = 200;
    private final int NB_MAX_BONS = 200;

    private String nom;
    private Article[] articles; // triés par numéro ISBN / ISSN
    private int nbArticles;
    private BonDepot[] bons;    // triés par date, le plus récent en dernier
    private int nbBons;
    private int dernierNumeroBon;

    public Etablissement(String nom) {
        this.nom = nom;
        articles = new Article[NB_MAX_ARTICLES];
        nbArticles = 0;
        bons = new BonDepot[NB_MAX_BONS];
        nbBons = 0;
        dernierNumeroBon = 0;
    }

    public int getNbArticles() {
        return nbArticles;
    }

    public int getNbBons() {
        return nbBons;
    }

    // ===================== Articles =====================

    // ajoute un article à sa place pour garder le tableau trié
    private boolean inserer(Article a) {
        if (nbArticles == NB_MAX_ARTICLES || rechercher(a.getNumero()) != null) {
            return false;
        }
        int i = nbArticles;
        while (i > 0 && articles[i - 1].placerApres(a)) {
            articles[i] = articles[i - 1];
            i--;
        }
        articles[i] = a;
        nbArticles++;
        return true;
    }

    // ajouter un livre
    public boolean ajouter(String description, double prix, int nb, String isbn, int nbPages) {
        if (isbn.length() != 13) {
            return false;
        }
        return inserer(new Livre(description, prix, nb, isbn, nbPages));
    }

    // ajouter un manuel
    public boolean ajouter(String description, double prix, int nb, String isbn, int nbPages,
            String matiere, String niveau) {
        if (isbn.length() != 13) {
            return false;
        }
        return inserer(new Manuel(description, prix, nb, isbn, nbPages, matiere, niveau));
    }

    // ajouter un magazine
    public boolean ajouter(String description, double prix, int nb, String issn,
            String periodicite, LocalDate datePublication) {
        if (issn.length() != 8) {
            return false;
        }
        return inserer(new Magazine(description, prix, nb, issn, periodicite, datePublication));
    }

    // retourne l'article qui a ce numéro, ou null
    public Article rechercher(String numero) {
        for (int i = 0; i < nbArticles; i++) {
            if (articles[i].getNumero().equals(numero)) {
                return articles[i];
            }
        }
        return null;
    }

    // augmenter le stock d'un article
    public boolean ajouter(String numero, int quantite) {
        Article a = rechercher(numero);
        if (a == null) {
            return false;
        }
        return a.ajouter(quantite);
    }

    // diminuer le stock d'un article
    public boolean retirer(String numero, int quantite) {
        Article a = rechercher(numero);
        if (a == null) {
            return false;
        }
        return a.retirer(quantite);
    }

    // ===================== Bons de dépôt =====================

    // enregistre un bon : numéro suivant, date du jour, puis mise à jour du stock
    public boolean ajouter(BonDepot bon) {
        if (nbBons == NB_MAX_BONS || bon.getNbArticles() == 0) {
            return false;
        }
        dernierNumeroBon++;
        bon.setNumero(dernierNumeroBon);
        bon.setDateEmission(LocalDate.now());
        // date du jour : le bon est le plus récent, il va à la fin du tableau
        bons[nbBons] = bon;
        nbBons++;
        for (int i = 0; i < bon.getNbArticles(); i++) {
            ajouter(bon.getLigne(i).getNumero(), bon.getLigne(i).getNbExemplaires());
        }
        return true;
    }

    // ===================== Listes =====================

    // articles avec leur prix du jour, du plus petit stock au plus grand
    public String lister() {
        // on trie une copie pour garder le tableau principal trié par numéro
        Article[] copie = new Article[nbArticles];
        for (int i = 0; i < nbArticles; i++) {
            copie[i] = articles[i];
        }
        // tri par sélection
        for (int i = 0; i < nbArticles - 1; i++) {
            int iMin = i;
            for (int j = i + 1; j < nbArticles; j++) {
                if (copie[j].getNbExemplaires() < copie[iMin].getNbExemplaires()) {
                    iMin = j;
                }
            }
            Article tmp = copie[i];
            copie[i] = copie[iMin];
            copie[iMin] = tmp;
        }
        String ch = "";
        for (int i = 0; i < nbArticles; i++) {
            ch = ch + copie[i] + " | PRIX DU JOUR : " + copie[i].calculerPrix() + " euros"
                    + System.lineSeparator();
        }
        return ch;
    }

    // bons d'un client
    public String lister(String telephone) {
        String ch = "";
        for (int i = 0; i < nbBons; i++) {
            if (bons[i].getTelephone().equals(telephone)) {
                ch = ch + bons[i] + System.lineSeparator();
            }
        }
        return ch;
    }

    // bons qui contiennent un article, entre deux dates (incluses)
    public String lister(String numero, LocalDate debut, LocalDate fin) {
        String ch = "";
        for (int i = 0; i < nbBons; i++) {
            LocalDate d = bons[i].getDateEmission();
            if (bons[i].contient(numero) && !d.isBefore(debut) && !d.isAfter(fin)) {
                ch = ch + bons[i] + System.lineSeparator();
            }
        }
        return ch;
    }

    // ===================== Fichiers =====================

    public void versFichierArticles(String nomF) throws IOException {
        FileWriter fw = new FileWriter(nomF, false);
        for (int i = 0; i < nbArticles; i++) {
            fw.write(articles[i].versFichier() + System.lineSeparator());
        }
        fw.close();
    }

    // le type est reconnu avec la longueur du numéro et le nombre de champs
    public void depuisFichierArticles(String nomF) throws IOException {
        FileReader fr = new FileReader(nomF);
        BufferedReader br = new BufferedReader(fr);
        String numero = br.readLine();
        while (numero != null) {
            String[] champs = br.readLine().split(" : ");
            String desc = champs[0];
            double prix = Double.valueOf(champs[1]);
            int nb = Integer.valueOf(champs[2]);
            if (numero.length() == 8) {
                ajouter(desc, prix, nb, numero, champs[3], LocalDate.parse(champs[4]));
            } else if (champs.length == 4) {
                ajouter(desc, prix, nb, numero, Integer.valueOf(champs[3]));
            } else {
                ajouter(desc, prix, nb, numero, Integer.valueOf(champs[3]), champs[4], champs[5]);
            }
            numero = br.readLine();
        }
        br.close();
    }

    public void versFichierDepots(String nomF) throws IOException {
        FileWriter fw = new FileWriter(nomF, false);
        for (int i = 0; i < nbBons; i++) {
            fw.write(bons[i].versFichier() + System.lineSeparator());
        }
        fw.close();
    }

    // le stock n'est pas modifié : il est déjà à jour dans le fichier des articles
    public void depuisFichierDepots(String nomF) throws IOException {
        FileReader fr = new FileReader(nomF);
        BufferedReader br = new BufferedReader(fr);
        String ligne = br.readLine();
        while (ligne != null && nbBons < NB_MAX_BONS) {
            int numero = Integer.valueOf(ligne);
            String[] entete = br.readLine().split(" : ");
            BonDepot bon = new BonDepot(numero, entete[0], LocalDate.parse(entete[1]));
            int n = Integer.valueOf(entete[2]);
            for (int k = 0; k < n; k++) {
                String[] l = br.readLine().split(" : ");
                bon.ajouterLigne(l[1], Integer.valueOf(l[0]));
            }
            // le fichier est déjà trié par date : on ajoute à la fin
            bons[nbBons] = bon;
            nbBons++;
            dernierNumeroBon = numero;
            ligne = br.readLine();
        }
        br.close();
    }

    @Override
    public String toString() {
        String ch = "=== " + nom + " ===" + System.lineSeparator()
                + nbArticles + " article(s) :" + System.lineSeparator();
        for (int i = 0; i < nbArticles; i++) {
            ch = ch + articles[i] + System.lineSeparator();
        }
        ch = ch + nbBons + " bon(s) de dépôt :" + System.lineSeparator();
        for (int i = 0; i < nbBons; i++) {
            ch = ch + bons[i] + System.lineSeparator();
        }
        return ch;
    }
}
