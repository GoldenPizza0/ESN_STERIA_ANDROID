package Entites;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Intervention {
    @SerializedName("No_contrat")
    private int No_contrat;
    @SerializedName("num_intervention")
    private int num_intervention;
    @SerializedName("intitule")
    private String intitule;
//    private Date debut;
//    private Date fin;
    @SerializedName("debut")
    private String debut;
    @SerializedName("fin")
    private String fin;
    @SerializedName("prix")
    private int prix;
    @SerializedName("etat")
    private String etat;
//    private int code_domaine;
    @SerializedName("domaine")
    private String domaine;

    public Intervention(int num_intervention, int no_contrat, String intitule, String debut, String fin, String etat, int prix, String domaine) {
        this.num_intervention = num_intervention;
        No_contrat = no_contrat;
        this.intitule = intitule;
        this.debut = debut;
        this.fin = fin;
        this.etat = etat;
        this.prix = prix;
        this.domaine = domaine;
//        this.code_domaine = code_domaine;
    }

    public int getNo_contrat() {
        return No_contrat;
    }

    public void setNo_contrat(int no_contrat) {
        No_contrat = no_contrat;
    }

    public int getNum_intervention() {
        return num_intervention;
    }

    public void setNum_intervention(int num_intervention) {
        this.num_intervention = num_intervention;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getDebut() {
        return debut;
    }

    public void setDebut(String debut) {
        this.debut = debut;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

//    public int getCode_domaine() {
//        return code_domaine;
//    }
//
//    public void setCode_domaine(int code_domaine) {
//        this.code_domaine = code_domaine;
//    }

    public String getDomaine() {
        return domaine;
    }
}
