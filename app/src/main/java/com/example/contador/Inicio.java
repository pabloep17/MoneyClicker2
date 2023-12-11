package com.example.contador;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import com.example.contador.Util.DataBaseUtils.DataBaseCreator;
import com.example.contador.Util.DataBaseUtils.DataBaseManager;
import com.example.contador.Util.MisPartidasAdapter;
import com.example.contador.Util.Player;

import java.io.Serializable;

public class Inicio extends AppCompatActivity {

    Player player;
    int uid = 0;

    DataBaseManager dataBaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        try {

            Bundle parametros = this.getIntent().getExtras();

            uid = parametros.getInt("uid");

            DataBaseCreator dataBaseCreator = new DataBaseCreator(this);

            dataBaseManager = new DataBaseManager(dataBaseCreator);

        }catch (RuntimeException e) {

        }

        Log.v("INICIO UID", uid+ "");

        player = dataBaseManager.getPlayerByID(uid);

        dataBaseManager.getGames(player.getUID()).stream().forEach(game -> {
            Log.v("Game", game.getCoinsPartida().toString());
        });

        this.setTitle("Inicio, Hola " + player.getName());

    }

    @Override
    protected void onResume() {
        super.onResume();

        player = dataBaseManager.getPlayerByID(uid);

        this.setTitle("Inicio, Hola " + player.getName());

    }

    public void jugar(View v) {

        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("contadorValue", player.getMonedas().toString());
        i.putExtra("manualClickValue", player.getManualClickValue().toString());
        i.putExtra("autoClickValue", player.getAutoClickValue().toString());
        i.putExtra("autoClickDelay", player.getAutoClickDelay());
        i.putExtra("uid", player.getUID());
        i.putExtra("idGame", dataBaseManager.newGame(player.getUID(), player.getMonedas().toString()));

        startActivity(i);

    }

    public void misPartidas(View v) {
        Intent i = new Intent(this, MisPartidas.class);
        i.putExtra("uid", player.getUID());
        startActivity(i);
    }

    public void info(View v) {
        Intent i = new Intent(this, Info.class);
        startActivity(i);
    }

    public void ajustes(View v) {
        Intent i = new Intent(this, Ajustes.class);
        i.putExtra("player", player);
        startActivity(i);
    }

}