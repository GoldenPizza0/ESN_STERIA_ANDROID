package Entites;

public class Salarie {
    private int id_salarie;
    private String nom_salarie;
    private String prenom_salarie;
    private String email;
    private String mdp;

    public Salarie(String nom_salarie, int id_salarie, String prenom_salarie, String email, String mdp) {
        this.nom_salarie = nom_salarie;
        this.id_salarie = id_salarie;
        this.prenom_salarie = prenom_salarie;
        this.email = email;
        this.mdp = mdp;
    }

    public int getId_salarie() {
        return id_salarie;
    }

    public void setId_salarie(int id_salarie) {
        this.id_salarie = id_salarie;
    }

    public String getNom_salarie() {
        return nom_salarie;
    }

    public void setNom_salarie(String nom_salarie) {
        this.nom_salarie = nom_salarie;
    }

    public String getPrenom_salarie() {
        return prenom_salarie;
    }

    public void setPrenom_salarie(String prenom_salarie) {
        this.prenom_salarie = prenom_salarie;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
}
