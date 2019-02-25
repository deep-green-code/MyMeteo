package com.bouchut.myfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bouchut.myfirstapplication.JsonClasses.CityInfo;
import com.bouchut.myfirstapplication.JsonClasses.DonneesMeteo;
import com.bouchut.myfirstapplication.JsonClasses.FcstDay;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {

    FcstDay m_day;
    DonneesMeteo m_DonneesMeteo;

    // Bloc d'info de ville
    RelativeLayout m_blocInfoVille;
    ImageView m_iconeVille;
    TextView m_infoVille;
    TextView m_sunsetVille;
    TextView m_windInfoVille;
    TextView m_otherInfoVille;
    TextView m_timeInfoVille;

    // Bloc d'info de Jour
    RelativeLayout m_blocInfoJour;
    ImageView m_iconeJour;
    TextView m_infoJour;
    TextView m_sunsetJour;
    TextView m_windInfoJour;
    TextView m_otherInfoJour;
    TextView m_timeInfoJour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // titre de l'activité
        setTitle(R.string.title_activity_detail);

        // Affiche la flèche de retour
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // configurer les infos de la page
        m_blocInfoVille = findViewById(R.id.blocInfoVille);
        m_iconeVille = findViewById(R.id.iconeVille);
        m_infoVille = findViewById(R.id.infoVille);
        m_sunsetVille = findViewById(R.id.sunsetVille);
        m_windInfoVille = findViewById(R.id.windInfoVille);
        m_otherInfoVille = findViewById(R.id.otherInfoVille);
        m_timeInfoVille = findViewById(R.id.timeInfoVille);

        m_blocInfoJour = findViewById(R.id.blocInfoJour);
        m_iconeJour = findViewById(R.id.iconeJour);
        m_infoJour = findViewById(R.id.infoJour);
        m_sunsetJour = findViewById(R.id.sunsetJour);
        m_windInfoJour = findViewById(R.id.windInfoJour);
        m_otherInfoJour = findViewById(R.id.otherInfoJour);
        m_timeInfoJour = findViewById(R.id.timeInfoJour);

        // on récupère les extras
        final Intent intent = getIntent();
        String jsonDay = intent.getStringExtra("jourCliqué");
        String jsonFullData= intent.getStringExtra("meteoData");

        // on converti le jour en objet Json
        try {
            m_day = new FcstDay(new JSONObject(jsonDay));
        } catch (JSONException e) {
            e.getStackTrace();
        }
        // on converti la ville en objet Json
        m_DonneesMeteo = new DonneesMeteo(jsonFullData);

        // Affichage des informations
        RefreshList(jsonDay);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /**
     * Création d'un menu dans la toolbar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.detail_activity_menu, menu);
        return true;
    }

    /**
     * Gestion des click sur les éléments du menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemAbout:
                break;
            case R.id.menuItemGmap:
                break;
            case R.id.menuItemPartageEmail:
                break;
            case android.R.id.home:
                // Gère le clique sur la flèche de retour arrière
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Sauvegarde de l'état et des infos du jour courant
     *
     * @param outState
     * @param outPersistentState
     */
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        if (m_day != null) {
            outState.putString("MeteoData", m_day.toJsonString());
        }
    }

    /**
     * Récupération de l'état et des infos du jour courant
     *
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String sJson = savedInstanceState.getString("MeteoData");

        if (sJson != null && !sJson.isEmpty()) {
            RefreshList(sJson);
        }
    }

    /**
     * Met à jour les informations de la page à partir de l'objet JSON du jour courant
     * @param jsonDay
     */
    private void RefreshList(String jsonDay) {
        // Création de l'objet DonneesMeteo qui contient le résultat
        try {
            m_day = new FcstDay(new JSONObject(jsonDay));
        } catch (JSONException e) {
            e.getStackTrace();
        }

        if (m_day != null) {
            // On complète le bloc d'infoVille
            m_infoVille.setText(String.format(getString(R.string.infoVille), m_DonneesMeteo.getCityInfo().getName()));
            m_sunsetVille.setText(String.format(getString(R.string.sunsetVille), m_DonneesMeteo.getCityInfo().getSunrise(), m_DonneesMeteo.getCityInfo().getSunset()));
            m_windInfoVille.setText(String.format(getString(R.string.windInfoVille), m_DonneesMeteo.getCurrentCondition().getWndSpd(), m_DonneesMeteo.getCurrentCondition().getWndDir()));
            m_otherInfoVille.setText(String.format(getString(R.string.otherInfoVille), m_DonneesMeteo.getCurrentCondition().getHumidity()));
            m_timeInfoVille.setText(String.format(getString(R.string.timeInfoVille), m_DonneesMeteo.getCurrentCondition().getHour()));

//            m_infoVille.setText(String.format(getString(R.string.infoVille), m_day.getCondition()));
//
//            m_windInfoVille.setText(String.format(getString(R.string.windInfoVille), 21, "S"));
//            m_otherInfoVille.setText(String.format(getString(R.string.otherInfoVille), m_day.getTmax()));
//            m_timeInfoVille.setText(String.format(getString(R.string.timeInfoVille), "09:00"));

            Picasso.get().load(m_day.getIconBig()).into(m_iconeJour);

        }
    }

}
