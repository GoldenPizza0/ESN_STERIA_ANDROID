package com.example.esn_steria_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Entites.Intervention;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailInterventionActivity extends AppCompatActivity {
    private int intervenantId;
    private Intervention intervention;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_inter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.itm_connecter) {
            Toast.makeText(this, "Vous avez cliqué sur \n se connecter", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }

        if(item.getItemId() == R.id.itm_voirsesinterventions) {
            Toast.makeText(this, "Vous avez cliqué sur \n voir mes candidatures", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, ListeInterventions.class);
            intent.putExtra("intervenantId", intervenantId);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_intervention);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intervenantId = getIntent().getIntExtra("intervenantId", -1);
        int interventionId = getIntent().getIntExtra("interventionId", -1);
        int noContrat = getIntent().getIntExtra("noContrat", -1);

        TextView tvIntitule = findViewById(R.id.tv_intitule);
        TextView tvNoContrat = findViewById(R.id.tv_nocontrat);
        TextView tvEtat = findViewById(R.id.tv_etat);
        TextView tvPrix = findViewById(R.id.tv_prix);
        TextView tvDebut = findViewById(R.id.tv_debut);
        TextView tvFin = findViewById(R.id.tv_fin);
        TextView tvDomaine = findViewById(R.id.tv_domaine);
        Button btnModifier = findViewById(R.id.ModifierButton);

        if (interventionId != -1) {
            Retrofit retrofit = RetrofitClient.getInstance();
            ApiService apiService = retrofit.create(ApiService.class);

            apiService.getIntervention(noContrat, interventionId).enqueue(new Callback<Intervention>() {
                @Override
                public void onResponse(Call<Intervention> call, Response<Intervention> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        intervention = response.body();

                        tvIntitule.setText(intervention.getIntitule());
                        tvNoContrat.setText("No Contrat : " + intervention.getNo_contrat());
                        tvEtat.setText(intervention.getEtat());
                        tvPrix.setText(intervention.getPrix() + "€");
                        tvDebut.setText(intervention.getDebut());
                        tvFin.setText(intervention.getFin());
                        tvDomaine.setText(intervention.getDomaine());
                    } else {
                        Toast.makeText(DetailInterventionActivity.this, "Erreur lors du chargement de l'intervention", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Intervention> call, Throwable t) {
                    Toast.makeText(DetailInterventionActivity.this, "Échec : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        btnModifier.setOnClickListener(v -> {
            if (intervenantId != -1 && interventionId != -1 && intervention != null) {
                Intent intent = new Intent(this, ModificationIntervention.class);
                intent.putExtra("interventionId", interventionId);
                intent.putExtra("noContrat", intervention.getNo_contrat());
                intent.putExtra("intitule", intervention.getIntitule());
                intent.putExtra("dateDebut", intervention.getDebut());
                intent.putExtra("dateFin", intervention.getFin());
                intent.putExtra("prix", String.valueOf(intervention.getPrix()));
                intent.putExtra("etat", intervention.getEtat());
                intent.putExtra("domaine", intervention.getDomaine());
                intent.putExtra("intervenantId", intervenantId);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Impossible de modifier : informations manquantes", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
