package com.example.esn_steria_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Entites.Intervenant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    String emailConnexion;
    String mdpConnexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onClickValider(View view) {
        // Ajoute ici l’action à exécuter lors du clic
        EditText emailInput = findViewById(R.id.editTextEmail);
        EditText mdpInput = findViewById(R.id.editTextPassword);

        emailConnexion = emailInput.getText().toString();
        mdpConnexion = mdpInput.getText().toString();

        if (!emailConnexion.isEmpty() && !mdpConnexion.isEmpty()) {
            fetchConnexion(/*emailConnexion, mdpConnexion*/);
        } else {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        }
    }
    private void fetchConnexion(/*String email, String mdp*/) {
        EditText emailInput = findViewById(R.id.editTextEmail);
        EditText mdpInput = findViewById(R.id.editTextPassword);

        String email = emailInput.getText().toString().trim();
        String mdp = mdpInput.getText().toString().trim();

        Retrofit retrofit = RetrofitClient.getInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        apiService.connexionIntervenant(email, mdp).enqueue(new Callback<Intervenant>() {
            @Override
            public void onResponse(Call<Intervenant> call, Response<Intervenant> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Intervenant user = response.body();
                    Toast.makeText(MainActivity.this, "Connexion réussie : " + user.getPrenom_salarie(), Toast.LENGTH_SHORT).show();

                    // Redirection
                    Intent intent = new Intent(MainActivity.this, ListeInterventions.class);
                    intent.putExtra("intervenantId", user.getId_salarie());
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Identifiants incorrects", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Intervenant> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
