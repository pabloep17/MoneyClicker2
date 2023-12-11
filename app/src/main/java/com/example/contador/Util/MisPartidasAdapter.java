package com.example.contador.Util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.contador.R;

import java.util.List;


public class MisPartidasAdapter extends RecyclerView.Adapter<MisPartidasAdapter.ViewHolder> {

    private List<Game> games;

    private Formatter numberFormater = new Formatter();

    public MisPartidasAdapter(List<Game> games) {
        this.games = games;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mispartidas_row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.fechaPartida.setText(numberFormater.formartDate(games.get(position).getFechaInicio()));
        holder.startCoins.setText(games.get(position).getInicioCoinsPartida().toString());
        holder.finishCoins.setText(games.get(position).getFinalCoinsPartida().toString());
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView fechaPartida;
        private TextView startCoins;
        private TextView finishCoins;
        public ViewHolder(View v) {
            super(v);
            fechaPartida = (TextView) v.findViewById(R.id.fechaPartida);
            startCoins = v.findViewById(R.id.startCoins);
            finishCoins = v.findViewById(R.id.finishCoins);
        }
    }

}