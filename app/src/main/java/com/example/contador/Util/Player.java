package com.example.contador.Util;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.math.BigInteger;

public class Player implements Comparable<Player>, Serializable {

    private int id;
    private String nick;
    private BigInteger monedas;

    private String password;

    private String lastPlay;

    private BigInteger manualClickValue;
    private BigInteger autoClickValue;
    private int autoClickDelay;

    public Player(int id, String nick, String password, BigInteger monedas, BigInteger manualClickValue, BigInteger autoClickValue, int autoClickDelay, String lastPlay) {
        this.id = id;
        this.nick = nick;
        this.password = password;
        this.monedas = monedas;
        this.manualClickValue = manualClickValue;
        this.autoClickValue = autoClickValue;
        this.autoClickDelay = autoClickDelay;
        this.lastPlay = lastPlay;
    }

    public Player(int id, String nick, String password, BigInteger monedas, BigInteger manualClickValue, BigInteger autoClickValue, int autoClickDelay) {
        this.id = id;
        this.nick = nick;
        this.password = password;
        this.monedas = monedas;
        this.manualClickValue = manualClickValue;
        this.autoClickValue = autoClickValue;
        this.autoClickDelay = autoClickDelay;
    }


    public String getName() {
        return nick;
    }

    public BigInteger getMonedas() {
        return monedas;
    }
    public BigInteger getManualClickValue() {
        return manualClickValue;
    }
    public BigInteger getAutoClickValue() {
        return autoClickValue;
    }
    public int getAutoClickDelay() {
        return autoClickDelay;
    }
    public String getLastPlay() { return lastPlay; }

    public int getUID() { return id; }

    public String getPassword() { return password; }


    @Override
    public String toString() {
        return "Name: " + nick + "\n monedas: " + monedas;
    }

    @Override
    public int compareTo(Player o) {
        return Double.compare(o.monedas.doubleValue(), this.monedas.doubleValue());
    }

}
