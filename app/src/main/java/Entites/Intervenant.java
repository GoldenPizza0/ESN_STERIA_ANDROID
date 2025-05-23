package Entites;

public class Intervenant extends Salarie{
    private String niveau_etude;
    private String maitrise_an;

    public Intervenant(String nom_salarie, int id_salarie, String prenom_salarie, String email, String mdp, String niveau_etude, String maitrise_an) {
        super(nom_salarie, id_salarie, prenom_salarie, email, mdp);
        this.niveau_etude = niveau_etude;
        this.maitrise_an = maitrise_an;
    }

    public String getNiveau_etude() {
        return niveau_etude;
    }

    public void setNiveau_etude(String niveau_etude) {
        this.niveau_etude = niveau_etude;
    }

    public String getMaitrise_an() {
        return maitrise_an;
    }

    public void setMaitrise_an(String maitrise_an) {
        this.maitrise_an = maitrise_an;
    }
}
