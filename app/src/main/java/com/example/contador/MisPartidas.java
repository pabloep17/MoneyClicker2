package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.contador.Util.DataBaseUtils.DataBaseCreator;
import com.example.contador.Util.DataBaseUtils.DataBaseManager;
import com.example.contador.Util.Game;
import com.example.contador.Util.MisPartidasAdapter;

import java.util.ArrayList;
import java.util.Collections;


public class MisPartidas extends AppCompatActivity {

    ArrayList<Game> games;
    DataBaseManager dataBaseManager;
    int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mispartidas);

        try {

            Bundle parametros = this.getIntent().getExtras();

            uid = parametros.getInt("uid");

            DataBaseCreator dataBaseCreator = new DataBaseCreator(this);

            dataBaseManager = new DataBaseManager(dataBaseCreator);

        }catch (RuntimeException e) {

        }

        this.setTitle("Mis Partidas");

        games = dataBaseManager.getGames(uid);

        Collections.sort(games);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.misPartidasRecicleView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // specify an adapter with the list to show
        MisPartidasAdapter misPartidasAdapter = new MisPartidasAdapter(games);
        recyclerView.setAdapter(misPartidasAdapter);

    }
}
