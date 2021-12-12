package com.yovix.tp05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "debug";
    ArrayAdapter<String> arrayAd;
    private Context myCon;
    ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myCon = this;

        //Obtenir l'instance de Spinner
        Spinner dropDown = (Spinner) findViewById(R.id.dropDown);
        dropDown.setOnItemSelectedListener(this);

        //Création de l'instance ArrayAdapter ayant la liste de jours (ArrayList)
        //Définition des données ArrayAdapter sur le Spinner
        ArrayList<String> listDays = new ArrayList<>();
        listDays.add("lundi");
        listDays.add("mardi");
        listDays.add("mercredi");
        listDays.add("jeudi");
        listDays.add("vendredi");
        listDays.add("samedi");
        listDays.add("dimanche");
        arrayAd = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listDays);
        arrayAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDown.setAdapter(arrayAd);

        //Récupérer votre selecteur de temps timePicker
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);

        //Laisser l’utilisateur choisir entre le mode AM/PM (setIs24HourView)
        timePicker.setIs24HourView(false);

        //Effectuer l'événement d'écoute setOnTimeChangedListener qui affiche un toast avec les valeurs
        //modifiées du sélecteur de temps comme indiqué dans la figure ci-dessus.
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(getApplicationContext(), "date selectionnée: " + hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
            }
        });

        ListView listV = (ListView) findViewById(R.id.listView);
        //La listView est initialement initialisé à {Faire du Sport} à travers un tableau de chaine de caractère
        /*
        String[] items = {"Faire du Sport"};
        ArrayAdapter<String> arrayListAd = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,items);
        listV.setAdapter(arrayListAd);
         */

        //Pour la liste de tache utiliser ArrayAdapter a une disposition avec un seul TextView.
        /*
        items.add("Faire du Sport");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.tache, R.id.itemTextView, items);
        listV.setAdapter(arrayAdapter);
         */

        //Ajouter un bouton « Ajouter TACHE » qui permet suite à l’evenement onClick, de remplir la liste View
        TextInputEditText input_text = (TextInputEditText) findViewById(R.id.input_text);
        Button add_task = (Button) findViewById(R.id.btn_add);
        /*
        add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Objects.requireNonNull(input_text.getText()).toString().isEmpty()) {
                    items.add(Objects.requireNonNull(input_text.getText()).toString());
                    //adapter.notifyDataSetChanged();
                    listV.invalidateViews();
                } else {
                    Toast.makeText(myCon, "input is empty", Toast.LENGTH_SHORT).show();
                }

            };
        });
        */

        //Le click sur un item de l’adapterView (notre ListView) déclenche l’évènement
        //setOnItemClickListener permettant d’afficher un toast avec la valeur de la tache sélectionnée.
        /*
        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String res = "You selected: " + String.valueOf(listV.getItemAtPosition(position));
                Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
            }
        });
         */

        //La listView est initialement initialisé à {Samedi | A 08 :00 | Faire du Sport} à travers une ArrayList
        ArrayList<HashMap<String, String>> values = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> hashM = new HashMap<>();
        hashM.put("Day", "Samedi");
        hashM.put("Hour", "A 8:00");
        hashM.put("Task", "Faire du Sport");
        values.add(hashM);
/*
        hashM.entrySet().forEach(entry -> {
            Log.i(TAG, entry.getKey() + " " + entry.getValue());

        });
*/

        //Pour la listView utiliser SimpleAdapter.
        String[] from = {"Day", "Hour", "Task"};
        int[] to = {R.id.itemTextView, R.id.itemTextView2, R.id.itemTextView3};
        SimpleAdapter adapter = new SimpleAdapter(this, values, R.layout.tache2, from, to);
        listV.setAdapter(adapter);

        //MAJ l’evenement onClick du bouton « Ajouter TACHE »
        add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Objects.requireNonNull(input_text.getText()).toString().isEmpty()) {
                    String day = dropDown.getSelectedItem().toString();
                    String capDay = day.substring(0, 1).toUpperCase() + day.substring(1).toLowerCase();
                    String hour = String.valueOf(timePicker.getHour());
                    String minutes = String.valueOf(timePicker.getMinute());
                    String name_task = Objects.requireNonNull(input_text.getText()).toString();
                    String capTask = name_task.substring(0, 1).toUpperCase() + name_task.substring(1).toLowerCase();
                    HashMap<String, String> hashNM = new HashMap<>();
                    hashNM.put("Day", capDay);
                    hashNM.put("Hour", "A " + hour + ":" + minutes);
                    hashNM.put("Task", capTask);
                    values.add(hashNM);
                    adapter.notifyDataSetChanged();
                    //listV.invalidateViews();
                } else {
                    Toast.makeText(myCon, "input is empty", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //Le click sur un item de l’adapterView (notre ListView) déclenche l’évènement
        //setOnItemClickListener permettant d’afficher dans une autre page un textView comportant les
        //valeurs de la tache sélectionnée.
        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent putI = new Intent(myCon, NextActivity.class);
                putI.putExtra("Selected Value", String.valueOf(listV.getItemAtPosition(position)));
                startActivity(putI);
            }
        });

    }

    //Exécution de l'action sur ItemSelected et onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //OnItemSelected déclenche l’affichage d’un Toast avec le jour choisi
        Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getApplicationContext(), "nothing selected", Toast.LENGTH_SHORT).show();
    }

    //Utiliser la méthode getCount
    public int getCount() {
        return items.size();
    }


}