package com.example.contador.Util;

import java.math.BigInteger;
import java.util.Date;

public class Game implements Comparable<Game> {

    int id;
    BigInteger startCoints;
    BigInteger finishCoins;
    String startTime;
    String finishTime;

    public Game(int id, BigInteger startCoints, BigInteger finishCoins, String startTime, String finishTime) {
        this.id = id;
        this.startCoints = startCoints;
        this.finishCoins = finishCoins;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    public BigInteger getCoinsPartida() {
        return finishCoins.subtract(startCoints);
    }

    public String getFechaInicio() {
        return startTime;
    }

    public BigInteger getInicioCoinsPartida() {
        return startCoints;
    }

    public BigInteger getFinalCoinsPartida() {
        return finishCoins;
    }

    @Override
    public int compareTo(Game otroJuego) {
        return new Date(otroJuego.getFechaInicio()).compareTo(new Date(this.getFechaInicio()));
    }
}
