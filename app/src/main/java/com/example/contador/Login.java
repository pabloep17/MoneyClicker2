package com.example.contador;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contador.Util.DataBaseUtils.DataBaseCreator;
import com.example.contador.Util.DataBaseUtils.DataBaseManager;
import com.example.contador.Util.Player;

import java.math.BigInteger;

public class Login  extends AppCompatActivity {
    Player player = new Player(0, "User not Found", "", new BigInteger("0"), new BigInteger("0"), new BigInteger("0"), 0);

    EditText nick;
    EditText pass;

    DataBaseManager dataBaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("Login");

        nick = findViewById(R.id.nickName);
        pass = findViewById(R.id.pass);

        DataBaseCreator dataBaseCreator = new DataBaseCreator(this);

        dataBaseManager = new DataBaseManager(dataBaseCreator);

    }

    public void login(View v) {

        player = dataBaseManager.getPlayer(nick.getText().toString(), pass.getText().toString());

        if (player.getUID() != 0) {
                Intent i = new Intent(this, Inicio.class);

                i.putExtra("uid", player.getUID());

            try {
                startActivity(i);
            }catch (IllegalStateException e) {
                e.printStackTrace();
            }

        } else {
            showAlertDialog("Usuario no encontrado", "No se he encontrado al usuario");
        }



    }

    public void singup(View v) {

        long uid = dataBaseManager.insertUser(nick.getText().toString(), pass.getText().toString());

        Log.v("UID Creado", String.valueOf(uid));

        if (uid > 0) {
            showAlertDialog("Usuario añadido", "Ya puedes iniciar sesion");
        } else if (uid == -1) {
            showAlertDialog("No se ha podido añadir el usuario", "El usuario ya existe");
        } else if (uid == -2) {
            showAlertDialog("No se he podido añadir el usuario", "Uno o varias campos estan vacios");
        }

    }

    public void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // You can perform actions when the "OK" button is clicked.
                dialog.dismiss(); // Close the dialog.
                try {
                    this.finalize();
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}