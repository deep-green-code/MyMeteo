package com.bouchut.myfirstapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bouchut.myfirstapplication.JsonClasses.CityInfo;
import com.bouchut.myfirstapplication.JsonClasses.DonneesMeteo;
import com.bouchut.myfirstapplication.JsonClasses.FcstDay;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    //Vérification de la présence d'infos
    Boolean m_exist = false;
    // Message d'accueil
    TextView m_textViewBienvenue;

    // Bloc de recherche
    AppCompatEditText m_editTextLocationHome;
    AppCompatImageButton m_imgSearchLocButton;

    // Bloc d'affichage d'erreur
    TextView m_JsonView;
    TextView m_ResultError;

    // Bloc d'info de ville
    RelativeLayout m_blocInfoVille;
    //ImageView m_iconeVille;
    TextView m_infoVille;
    TextView m_sunsetVille;
    TextView m_windInfoVille;
    TextView m_otherInfoVille;
    TextView m_timeInfoVille;

    // Bloc liste de résultat
    ListView m_listViewHome;
    SwipeRefreshLayout m_listRefresher;
    DonneesMeteo m_DonneesMeteo;
    MeteoAdapter m_adapter;
    List<FcstDay> m_lsDays = new ArrayList<>();

    // URL de l'API météo
    final String m_urlBaseMeteo = "https://www.prevision-meteo.ch/services/json/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // titre de l'activité
        setTitle(R.string.title_activity_home);

        // Affiche la flèche de retour
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // on récupère les infos de l'activité précédente
        final Intent intent = getIntent();
        String sUsername = intent.getStringExtra("username");

        // on récupère les valeurs de la page
        m_textViewBienvenue = findViewById(R.id.textViewBienvenue);
        m_editTextLocationHome = findViewById(R.id.editTextLocationHome);
        m_imgSearchLocButton = findViewById(R.id.imgSearchLocButton);

        // configurer le bloc infoVille
        m_blocInfoVille = findViewById(R.id.blocInfoVille);
        m_infoVille = findViewById(R.id.infoVille);
        m_sunsetVille = findViewById(R.id.sunsetVille);
        m_windInfoVille = findViewById(R.id.windInfoVille);
        m_otherInfoVille = findViewById(R.id.otherInfoVille);
        m_timeInfoVille = findViewById(R.id.timeInfoVille);
        m_blocInfoVille.setVisibility(View.GONE);


        m_listViewHome = findViewById(R.id.listViewHome);
        //configurer la listView
        m_adapter = new MeteoAdapter(this);
        m_listViewHome.setAdapter(m_adapter);


        //Configurer le refresher
        m_listRefresher = findViewById(R.id.listRefresher);
        m_listRefresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                DownloadData task = new DownloadData(HomeActivity.this);
//                task.execute();
                RefreshList(m_DonneesMeteo.toJsonString());
            }
        });

        // préparation et affichage du message d'accueil en cas de valeur
        if (!sUsername.equals("")) {
            String sText = getString(R.string.home_label_firstLogin);
            String sFormatedText = String.format(sText, sUsername);
            m_textViewBienvenue.setText(sFormatedText);
        }

        // Ecoute du bouton de recherche
        m_imgSearchLocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchLocButtonClick();
                Utils.hideSoftKeyboard(HomeActivity.this); // on cache le clavier à validation
            }
        });
        // Ecoute de la validation du champ de recherche
        m_editTextLocationHome.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        SearchLocButtonClick();
                        Utils.hideSoftKeyboard(HomeActivity.this); // on cache le clavier à validation
                        return true;
                    }
                }
                return false;
            }
        });

        // Ecoute de click sur la liste
        m_listViewHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FcstDay dayClicked = m_lsDays.get(position);
                String fullData = m_DonneesMeteo.toJsonString();

                Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
                intent.putExtra("jourCliqué", dayClicked.toJsonString());
                intent.putExtra("meteoData", fullData);
                startActivity(intent);
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
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.action_settings:
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

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        if (m_DonneesMeteo != null) {
            outState.putString("MeteoData", m_DonneesMeteo.toJsonString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String sJson = savedInstanceState.getString("MeteoData");

        if (sJson != null && !sJson.isEmpty()) {
            RefreshList(sJson);
        }
    }

    private void SearchLocButtonClick() {

        m_listViewHome = findViewById(R.id.listViewHome);
        m_listRefresher = findViewById(R.id.listRefresher);
        m_JsonView = findViewById(R.id.textViewResultJson);
        m_ResultError = findViewById(R.id.textViewResultError);

        m_JsonView.setVisibility(View.GONE);
        m_ResultError.setVisibility(View.GONE);

        // on vérifie que le champs ne soit pas vide
        if (m_editTextLocationHome.getText().toString().isEmpty()) {
            //show error
            String sErrorMsg = getString(R.string.errorCityEmpty);
            m_editTextLocationHome.setError(sErrorMsg);
        } else {
            DownloadData task = new DownloadData(HomeActivity.this);
            task.execute();
        }

    }

    private class DownloadData extends AsyncTask<String, Integer, Integer> {

        Context m_context;

        DownloadData(Context monParam) {
            m_context = monParam;
        }

        @Override
        protected void onPreExecute() {
            //on active le refresh
            m_listRefresher.setRefreshing(true);
            //on vide la liste actuelle
            m_lsDays.clear();
            //on enregistre la liste vide dans l'adapteur pour effacer les anciennes données
            m_adapter.setList(m_lsDays);
            //on cache le bloc info Ville
            m_blocInfoVille.setVisibility(View.GONE);
        }

        @Override
        protected Integer doInBackground(String... strings) {
            //appel de l'API pour récupérer le JSON
            // on active le refresh

            String sNewLocation = m_editTextLocationHome.getText().toString().replace(' ', '-');

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(m_context);
            String url = m_urlBaseMeteo + sNewLocation.toLowerCase();

// Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                // on récupère la réponse pour tester sa validité
                                JSONObject object = new JSONObject(response);

                                if (response.indexOf("error") > -1) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                                    builder.setTitle(getString(R.string.err));
                                    builder.setMessage(getString(R.string.ville_err_notfound));
                                    builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    builder.show();
                                } else {
                                    RefreshList(response);
                                }
                            } catch (JSONException e) {
                                // en cas d'erreur (problème ou retour JSON incorrect) : on affiche l'erreur.
                                String errorRsult = e.getStackTrace().toString();
                                m_JsonView.setText(errorRsult);
                                m_JsonView.setHeight(0);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    m_JsonView.setText(getString(R.string.search_location_error));
                    m_ResultError.setText(getString(R.string.search_location_no_response));
                    m_JsonView.setVisibility(View.VISIBLE);
                    m_ResultError.setVisibility(View.VISIBLE);
                }
            });

// Add the request to the RequestQueue.
            queue.add(stringRequest);
            return 1;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            m_listRefresher.setRefreshing(false);
        }
    }

    private void RefreshList(String response) {
        // Création de l'objet DonneesMeteo qui contient le résultat
        m_DonneesMeteo = new DonneesMeteo(response);

        // On complète le bloc d'infoVille
        m_infoVille.setText(String.format(getString(R.string.infoVille), m_DonneesMeteo.getCityInfo().getName()));
        m_sunsetVille.setText(String.format(getString(R.string.sunsetVille), m_DonneesMeteo.getCityInfo().getSunrise(), m_DonneesMeteo.getCityInfo().getSunset()));
        m_windInfoVille.setText(String.format(getString(R.string.windInfoVille), m_DonneesMeteo.getCurrentCondition().getWndSpd(), m_DonneesMeteo.getCurrentCondition().getWndDir()));
        m_otherInfoVille.setText(String.format(getString(R.string.otherInfoVille), m_DonneesMeteo.getCurrentCondition().getHumidity()));
        m_timeInfoVille.setText(String.format(getString(R.string.timeInfoVille), m_DonneesMeteo.getCurrentCondition().getHour()));
        // On affiche le bloc infoVille
        m_blocInfoVille.setVisibility(View.VISIBLE);

        m_lsDays.clear();
        // On crée la liste
        m_lsDays.add(m_DonneesMeteo.getFcstDay0());
        m_lsDays.add(m_DonneesMeteo.getFcstDay1());
        m_lsDays.add(m_DonneesMeteo.getFcstDay2());
        m_lsDays.add(m_DonneesMeteo.getFcstDay3());
        m_lsDays.add(m_DonneesMeteo.getFcstDay4());
        // et on l'insère dans l'adapteur
        m_adapter.setList(m_lsDays);

        m_listRefresher.setRefreshing(false);
    }


}
