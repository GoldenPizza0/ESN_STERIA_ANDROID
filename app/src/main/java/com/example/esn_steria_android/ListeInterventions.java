package com.example.esn_steria_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Entites.Intervention;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListeInterventions extends AppCompatActivity {
    private RecyclerView recyclerView;
    private InterventionAdapter interventionAdapter;

    private SearchView searchView;
    private int intervenantId;

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_inter, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        if(item.getItemId()== R.id.itm_connecter) {
            Toast.makeText(this, "Vous avez cliqué sur \n se connecter", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            return true;
        }
        if (item.getItemId()== R.id.itm_voirsesinterventions) {
            Toast.makeText(this, "Vous avez cliqué sur \n voir mes candidatures", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, ListeInterventions.class);
            intent.putExtra("intervenantId", intervenantId);
            startActivity(intent);

            return true;
        } else
            return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listeinterventions);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intervenantId = getIntent().getIntExtra("intervenantId", -1);

        recyclerView = findViewById(R.id.rv_liste);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (interventionAdapter != null) {
                    interventionAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });

        fetchInterventions();

    }

    private void fetchInterventions() {
        Retrofit retrofit = RetrofitClient.getInstance();
        ApiService apiService = retrofit.create(ApiService.class);


        apiService.getInterventions(intervenantId).enqueue(new Callback<List<Intervention>>() {
            @Override
            public void onResponse(Call<List<Intervention>> call, Response<List<Intervention>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Intervention> interventions = response.body();
                    interventionAdapter = new InterventionAdapter(interventions, intervention -> {
                        Intent intent = new Intent(ListeInterventions.this, DetailInterventionActivity.class);
                        intent.putExtra("interventionId", intervention.getNum_intervention());
                        intent.putExtra("noContrat", intervention.getNo_contrat());
                        intent.putExtra("intitule", intervention.getIntitule());
                        intent.putExtra("debut", intervention.getDebut());
                        intent.putExtra("fin", intervention.getFin());
                        intent.putExtra("prix", intervention.getPrix());
                        intent.putExtra("etat", intervention.getEtat());
                        intent.putExtra("domaine", "");
                        intent.putExtra("intervenantId", intervenantId);

                        startActivity(intent);
                    });
                    recyclerView.setAdapter(interventionAdapter);

                    recyclerView.setAdapter(interventionAdapter);
                } else {
                    Toast.makeText(ListeInterventions.this, "Erreur de chargement", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<List<Intervention>> call, Throwable t) {
                Toast.makeText(ListeInterventions.this, "Échec : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}