package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.contador.Util.DataBaseUtils.DataBaseCreator;
import com.example.contador.Util.DataBaseUtils.DataBaseManager;
import com.example.contador.Util.Player;

import java.math.BigInteger;

public class Ajustes extends AppCompatActivity {

    DataBaseManager dataBaseManager;
    Player player;

    EditText nickUpdate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        this.setTitle("Ajustes");
        DataBaseCreator dataBaseCreator = new DataBaseCreator(this);

        dataBaseManager = new DataBaseManager(dataBaseCreator);

        Bundle parametros = this.getIntent().getExtras();
        player = (Player) parametros.getSerializable("player");

        nickUpdate = findViewById(R.id.nickUpdate);

        nickUpdate.setText(player.getName());

    }

    public void safe(View v){

        dataBaseManager.updateUser(player.getUID(), "nick", nickUpdate.getText().toString());

        dataBaseManager.deleteAllUsers();

        Intent i = new Intent(this, Inicio.class);
        startActivity(i);
        this.finish();

    }

    public void deleteUser(View v) {
        dataBaseManager.deleteUser(player.getUID());
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }

}