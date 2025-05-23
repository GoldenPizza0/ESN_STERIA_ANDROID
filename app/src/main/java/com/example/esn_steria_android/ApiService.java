package com.example.esn_steria_android;

import java.util.List;

import Entites.Intervenant;
import Entites.Intervention;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("connexion/{email}/{mdp}/")
    Call<Intervenant> connexionIntervenant(@Path("email") String email, @Path("mdp") String mdp);
    @GET("interventions/intervenant/{id}/")
    Call<List<Intervention>> getInterventions(@Path("id") int idIntervenant);

    @GET("intervention/{noContrat}/{idInter}/")
    Call<Intervention> getIntervention(@Path("noContrat") int noContrat, @Path("idInter") int idIntervention);

    @PUT("intervention/modif/")
    Call<Void> modifierIntervention(@Body Intervention intervention);
}
