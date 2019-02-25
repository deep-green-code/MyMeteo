package com.bouchut.myfirstapplication;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bouchut.myfirstapplication.JsonClasses.FcstDay;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeteoAdapter extends BaseAdapter {

    List<FcstDay> m_lsDays;
    Context m_context;

    public MeteoAdapter(Context context) {
        m_lsDays = new ArrayList<>();
        m_context = context;
    }

    public void setList(List<FcstDay> listToShow) {
        // on vide les éléments
        m_lsDays.clear();
        // pour y mettre les nouveaux éléments définis
        m_lsDays.addAll(listToShow);

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return m_lsDays.size();
    }

    @Override
    public Object getItem(int position) {
        return m_lsDays.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // on crée la vue à partir du XML
        View v = LayoutInflater.from(m_context).inflate(R.layout.meteo_list_view, parent, false);

        // on récupère les éléments de la vue
        AppCompatImageView imgTemps = v.findViewById(R.id.iconeDetails);
        AppCompatTextView tempsDetail = v.findViewById(R.id.tempsDetails);
        AppCompatTextView conditionDetails = v.findViewById(R.id.conditionDetails);
        AppCompatTextView temperatureMin = v.findViewById(R.id.temperatureMin);
        AppCompatTextView temperatureMax = v.findViewById(R.id.temperatureMax);

        // configuration des vues par rapport au résultat
        FcstDay dayToShow = (FcstDay)getItem(position);

        // on défini les texts à afficher
        String mainText;
        try {
            Date dateRecup = new SimpleDateFormat("dd.MM.yyyy").parse(dayToShow.getDate());
            String sFormatedDate = new SimpleDateFormat("dd MMMM").format(dateRecup);
            mainText = dayToShow.getDayLong() + " " + sFormatedDate;
        } catch (ParseException e) {
            mainText = dayToShow.getDayLong() + " - " + dayToShow.getDate().split("[.]+")[0] + "/" + dayToShow.getDate().split("[.]+")[1] + "/" + dayToShow.getDate().split("[.]+")[2];
            //e.getStackTrace();
        }

        // affichage des textes
        tempsDetail.setText(mainText);
        conditionDetails.setText(dayToShow.getCondition());
        temperatureMin.setText(String.valueOf(dayToShow.getTmin()) + "°C");
        temperatureMax.setText(String.valueOf(dayToShow.getTmax()) + "°C");
//        conditionDetails.setText("température minimale : " + dayToShow.getTmin() + "C° - maximale : " + dayToShow.getTmax() + "C°");

        // récupération et affichage de l'image
        Picasso.get().load(dayToShow.getIcon()).into(imgTemps);

        return v;
    }
}
