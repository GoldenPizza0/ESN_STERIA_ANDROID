package com.example.esn_steria_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class ModificationIntervention extends AppCompatActivity {
    private int intervenantId, interventionId, noContrat;
    private EditText intituleInput, dateDebutInput, dateFinInput, prixInput;
    private RadioGroup etatRadioGroup;
    private RadioButton etatAFaire, etatEnCours, etatTermine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modification_intervention);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialisation des vues
        intituleInput = findViewById(R.id.intituleInput);
        dateDebutInput = findViewById(R.id.dateDebutInput);
        dateFinInput = findViewById(R.id.dateFinInput);
        prixInput = findViewById(R.id.prixInput);
        etatRadioGroup = findViewById(R.id.etatRadioGroup);
        etatAFaire = findViewById(R.id.etatAFaire);
        etatEnCours = findViewById(R.id.etatEnCours);
        etatTermine = findViewById(R.id.etatTermine);

        // Récupération des données de l'intent
        Intent intent = getIntent();
        String intitule = intent.getStringExtra("intitule");
        String dateDebut = intent.getStringExtra("dateDebut");
        String dateFin = intent.getStringExtra("dateFin");
        int prix = Integer.parseInt(intent.getStringExtra("prix"));
        String etat = intent.getStringExtra("etat");
        intervenantId = intent.getIntExtra("intervenantId", -1);
        interventionId = intent.getIntExtra("interventionId", -1);
        noContrat = intent.getIntExtra("noContrat", -1);

        // Préremplissage des champs
        intituleInput.setText(intitule);
        dateDebutInput.setText(dateDebut);
        dateFinInput.setText(dateFin);
        prixInput.setText(String.valueOf(prix));

        if ("À faire".equalsIgnoreCase(etat)) etatAFaire.setChecked(true);
        else if ("En cours".equalsIgnoreCase(etat)) etatEnCours.setChecked(true);
        else if ("Terminé".equalsIgnoreCase(etat)) etatTermine.setChecked(true);
    }

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

    public void onClickValider(View view) {
        String intitule = intituleInput.getText().toString().trim();
        String dateDebut = dateDebutInput.getText().toString().trim();
        String dateFin = dateFinInput.getText().toString().trim();
        String prixStr = prixInput.getText().toString().trim();

        int prix;
        try {
            prix = Integer.parseInt(prixStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Montant invalide", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedEtatId = etatRadioGroup.getCheckedRadioButtonId();
        if (selectedEtatId == -1) {
            Toast.makeText(this, "Veuillez sélectionner un état", Toast.LENGTH_SHORT).show();
            return;
        }
        String etat = ((RadioButton) findViewById(selectedEtatId)).getText().toString();

        if (intitule.isEmpty() || dateDebut.isEmpty() || dateFin.isEmpty() || etat.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        String domaine = getIntent().getStringExtra("domaine");
        Intervention intervention = new Intervention(noContrat, interventionId, intitule, dateDebut, dateFin, etat, prix, domaine);

        Retrofit retrofit = RetrofitClient.getInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.modifierIntervention(intervention).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ModificationIntervention.this, "Modification réussie", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ModificationIntervention.this, ListeInterventions.class);
                    intent.putExtra("intervenantId", intervenantId);
                    startActivity(intent);
                } else {
                    Toast.makeText(ModificationIntervention.this, "La modification a échoué", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ModificationIntervention.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

