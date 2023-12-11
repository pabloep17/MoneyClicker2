package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contador.Util.Mejora;
import com.example.contador.Util.Formatter;

import java.math.BigInteger;
import java.util.ArrayList;

public class Mejoras extends AppCompatActivity {

    Formatter nf = new Formatter();

    private ListView listView;
    private ArrayList<Mejora> items = new ArrayList<>();

    BigInteger contadorValue;
    BigInteger manualClickValue;
    BigInteger autoClickValue;

    int autoClickDelay;

    int uid;
    long idGame;

    TextView monedasTotal;
    @SuppressLint("UseSupportActionBar")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mejoras);
        this.setTitle("Mejoras");

        monedasTotal = findViewById(R.id.monedasTotal);

        Bundle parametros = this.getIntent().getExtras();
        contadorValue = new BigInteger(parametros.getString("contadorValue"));
        manualClickValue = new BigInteger(parametros.getString("manualClickValue"));
        autoClickValue = new BigInteger(parametros.getString("autoClickValue"));
        autoClickDelay = parametros.getInt("autoClickDelay");
        uid = parametros.getInt("uid");
        idGame = parametros.getLong("idGame");

        listView = findViewById(R.id.listView);

        items.add(new Mejora(R.drawable.unamoneda, "Incrementar Click Manual", "incManualClickValue", new BigInteger("5"), manualClickValue));
        items.add(new Mejora(R.drawable.unamoneda, "Incrementar Click Automatico", "incAutoClickValue", new BigInteger("25"), autoClickValue));
        items.add(new Mejora(R.drawable.unamoneda, "Incrementar Velocidad Auto Click", "incAutoClickDelay", new BigInteger("30"), autoClickDelay));

        CustomAdapter adapter = new CustomAdapter(items);
        listView.setAdapter(adapter);

        updateUI();

        autoClick();

    }

    public void updateUI() {
        monedasTotal.setText(nf.formarNumber(contadorValue));
    }

    public void volverJuego(View v) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("contadorValue", contadorValue.toString());
        i.putExtra("manualClickValue", manualClickValue.toString());
        i.putExtra("autoClickValue", autoClickValue.toString());
        i.putExtra("autoClickDelay", autoClickDelay);
        i.putExtra("uid", uid);
        i.putExtra("idGame", idGame);
        startActivity(i);
        this.finish();
    }

    // Adaptador personalizado
    private class CustomAdapter extends BaseAdapter {
        private ArrayList<Mejora> items;

        public CustomAdapter(ArrayList<Mejora> items) {
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint({"ResourceAsColor", "ResourceType"})
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.mejoras_list_layout, parent, false);
            }

            Mejora item = items.get(position);

            ImageView imageView = convertView.findViewById(R.id.imageView);
            TextView textView = convertView.findViewById(R.id.textView);
            Button button = convertView.findViewById(R.id.button);
            button.setText(nf.formarNumber(item.getPrice()) + " coins");
            
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (item.getButtonFuncion()) {
                        case "incManualClickValue":
                            incManualClickValue(item);
                            button.setText(nf.formarNumber(item.getPrice()) + " coins");
                            break;
                        case "incAutoClickValue":
                            incAutoClickValue(item);
                            button.setText(nf.formarNumber(item.getPrice()) + " coins");
                            break;
                        case "incAutoClickDelay":
                            incAutoClickDelay(item);
                            button.setText(nf.formarNumber(item.getPrice()) + " coins");
                            break;
                        default:
                            break;
                    }

                }
            });

            textView.setText(item.getText());

            imageView.setImageResource(item.getImageResId());
            textView.setText(item.getText());

            return convertView;
        }
    }

    public void incManualClickValue(Mejora m) {

        if (contadorValue.add(new BigInteger("-" + m.getPrice())).compareTo(new BigInteger("0")) > -1) {

            manualClickValue = manualClickValue.add(new BigInteger("1"));

            contadorValue = contadorValue.add(new BigInteger("-" + m.getPrice()));

            m.incPrice(manualClickValue);

            updateUI();

        }else{
            Toast.makeText(getApplicationContext(), "No tienes sufientes coins", Toast.LENGTH_SHORT).show();
        }

    }

    public void incAutoClickValue(Mejora m) {

        if (contadorValue.add(new BigInteger("-" + m.getPrice())).compareTo(new BigInteger("0")) > -1) {

            autoClickValue = autoClickValue.add(new BigInteger("1"));

            contadorValue = contadorValue.add(new BigInteger("-" + m.getPrice()));

            m.incPrice(autoClickValue);

            updateUI();

        }else{
            Toast.makeText(getApplicationContext(), "No tienes sufientes coins", Toast.LENGTH_SHORT).show();
        }

    }

    public void incAutoClickDelay(Mejora m) {
        if (contadorValue.add(new BigInteger("-" + m.getPrice())).compareTo(new BigInteger("0")) > -1 && (autoClickDelay - 50) > 0 ){

            autoClickDelay -= 50;

            contadorValue = contadorValue.add(new BigInteger("-" + m.getPrice()));

            m.incPrice(autoClickDelay);

            updateUI();

        }else if (contadorValue.compareTo(new BigInteger("50")) == 0){
            Toast.makeText(getApplicationContext(), "No tienes sufientes coins", Toast.LENGTH_SHORT).show();
        }
    }

    void autoClick() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(autoClickDelay);
                    sumar(autoClickValue);
                    runOnUiThread(() -> updateUI());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sumar(BigInteger incremento) {
        contadorValue = contadorValue.add(incremento);
    }

}