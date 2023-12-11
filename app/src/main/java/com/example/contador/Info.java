package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.contador.Util.DataBaseUtils.DataBaseCreator;
import com.example.contador.Util.DataBaseUtils.DataBaseManager;
import com.example.contador.Util.UserAdapter;
import com.example.contador.Util.Player;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Info extends AppCompatActivity {

    List<Player> players = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        this.setTitle("Informacion");


        DataBaseCreator dataBaseCreator = new DataBaseCreator(this);

        DataBaseManager dataBaseManager = new DataBaseManager(dataBaseCreator);

        players = dataBaseManager.getPlayers();

        Collections.sort(players);

        RecyclerView reyclerViewUser = (RecyclerView) findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        reyclerViewUser.setHasFixedSize(true);

        // use a linear layout manager
        reyclerViewUser.setLayoutManager(new LinearLayoutManager(this));

        // specify an adapter with the list to show
        UserAdapter mAdapter = new UserAdapter(players);
        reyclerViewUser.setAdapter(mAdapter);

    }




}