package com.bouchut.myfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class LoginActivity extends AppCompatActivity {

    AppCompatButton m_monButtonLogin;
    AppCompatEditText m_editTextLogin;
    AppCompatEditText m_editTextPassword;

    /**
     * Première méthode appelée par l'application
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //lance le chargement du XML
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // on récupère les valeurs de la page
        m_monButtonLogin = findViewById(R.id.loginButtonHome);
        m_editTextLogin = findViewById(R.id.editTextLoginHome);
        m_editTextPassword = findViewById(R.id.editTextPassHome);

        m_monButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManageConnexionButtonClick();
            }
        });
    }

    private void ManageConnexionButtonClick() {
//        m_editTextLogin = findViewById(R.id.editTextLoginHome);
//        m_editTextPassword = findViewById(R.id.editTextPassHome);

        //récupération des valeurs des champs utilisateur
        String sUserName = m_editTextLogin.getText().toString();
        String sPassword = m_editTextPassword.getText().toString();

        //vérification de mot de passe sommaire
//        if (sUserName == "admin" && sPassword == "1234") {
            //formatage de ligne
            String sTextBase = getString(R.string.snackbar_welcome);
            String sTexcomplet = String.format(sTextBase, sUserName);

            Snackbar.make(m_editTextLogin,  sTexcomplet, Snackbar.LENGTH_LONG).show();

            //Démarre une nouvelle activité
            Intent intent = new Intent(this, HomeActivity.class);

            intent.putExtra("username", sUserName);

            //Appel de l'activité choisie
            startActivity(intent);
//        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
