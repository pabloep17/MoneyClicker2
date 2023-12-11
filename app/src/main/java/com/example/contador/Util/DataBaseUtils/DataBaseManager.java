package com.example.contador.Util.DataBaseUtils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.contador.Util.Game;
import com.example.contador.Util.Player;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;

public class DataBaseManager implements Serializable {

    //TODO: Evitar que se loggen dos usuarios con el mismo nicj
    private SQLiteDatabase db;
    public DataBaseManager(DataBaseCreator dataBaseCreator) {
        this.db = dataBaseCreator.getWritableDatabase();
    }

    @SuppressLint("Range")
    public ArrayList<Player> getPlayers() {

        ArrayList<Player> players = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT _id, nick, password, coins, manualClickValue, autoClickValue, autoClickDelay, lastPlay FROM users", null);

        if (c != null) {
            c.moveToFirst();
            do {

                String nick = c.getString(c.getColumnIndex("nick"));
                int id = c.getInt(c.getColumnIndex("_id"));
                String coins = c.getString(c.getColumnIndex("coins"));
                String password = c.getString(c.getColumnIndex("password"));
                String manualClickValue = c.getString(c.getColumnIndex("coins")); //manualClickValue
                String autoClickValue = c.getString(c.getColumnIndex("autoClickValue")); //autoClickValue
                int autoClickDelay = c.getInt(c.getColumnIndex("autoClickDelay")); //autoClickDelay
                String lastPlay = c.getString(c.getColumnIndex("lastPlay"));
                players.add(new Player(id, nick, password, new BigInteger(coins), new BigInteger(manualClickValue), new BigInteger(autoClickValue), autoClickDelay, lastPlay));

            } while (c.moveToNext());
        }
        c.close();

        return players;

    }

    @SuppressLint("Range")
    public Player getPlayer(String nick, String pass) {

        String sql = "SELECT _id, nick, password, coins, manualClickValue, autoClickValue, autoClickDelay, lastPlay FROM users WHERE nick = '" + nick + "' AND password = '" + sha256(pass) +"'";

        Log.v("SQL SENTENCE", sql);

        Cursor c = db.rawQuery(sql, null);

        if (c != null) {

            if (c.moveToFirst()) {
                int id = c.getInt(c.getColumnIndex("_id"));
                String coins = c.getString(c.getColumnIndex("coins"));
                String password = c.getString(c.getColumnIndex("password"));
                String manualClickValue = c.getString(c.getColumnIndex("manualClickValue")); //manualClickValue
                String autoClickValue = c.getString(c.getColumnIndex("autoClickValue")); //autoClickValue
                int autoClickDelay = c.getInt(c.getColumnIndex("autoClickDelay")); //autoClickDelay
                String lastPlay = c.getString(c.getColumnIndex("lastPlay"));
                return new Player(id, nick, password, new BigInteger(manualClickValue), new BigInteger(autoClickValue),  new BigInteger(coins), autoClickDelay, lastPlay);
            }

        }

        return new Player(0, "User not Found", "", new BigInteger("0"), new BigInteger("0"), new BigInteger("0"), 0);

    }
     public long insertUser(String nick, String pass) {

        if (!nick.isEmpty() && !pass.isEmpty()) {

            ContentValues cv = new ContentValues();
            cv.put("nick", nick);
            cv.put("password", sha256(pass));
            cv.put("coins", "0");
            cv.put("manualClickValue", "1");
            cv.put("autoClickValue", "0");
            cv.put("autoClickDelay", 3000);
            cv.put("lastPlay", new Date().toString());

            return db.insert("users", null, cv);

        } else {
            return -2;
        }

    }

    @SuppressLint("Range")
    public Player getPlayerByID(int uid) {

        String sql = "SELECT _id, nick, password, coins, manualClickValue, autoClickValue, autoClickDelay, lastPlay FROM users WHERE _id = " + uid ;

        Log.v("SQL SENTENCE", sql);

        Cursor c = db.rawQuery(sql, null);

        if (c != null) {

            if (c.moveToFirst()) {
                int id = c.getInt(c.getColumnIndex("_id"));
                String nick = c.getString(c.getColumnIndex("nick"));
                String coins = c.getString(c.getColumnIndex("coins"));
                String password = c.getString(c.getColumnIndex("password"));
                String manualClickValue = c.getString(c.getColumnIndex("manualClickValue"));
                String autoClickValue = c.getString(c.getColumnIndex("autoClickValue"));
                int autoClickDelay = c.getInt(c.getColumnIndex("autoClickDelay"));
                String lastPlay = c.getString(c.getColumnIndex("lastPlay"));
                return new Player(id, nick, password, new BigInteger(coins), new BigInteger(manualClickValue), new BigInteger(autoClickValue), autoClickDelay, lastPlay);
            }

        }
        return new Player(0, "User not Found", "", new BigInteger("0"), new BigInteger("0"), new BigInteger("0"), 0);


    }

    public void updateUser(int id, String dato, String valor) {
        String sql = "UPDATE users SET " + dato + " = '" + valor + "' WHERE _id = " + id;

        Log.v("SQL SENTENCE", sql);

        db.execSQL(sql);

    }

    public void deleteAllUsers() {

        String sql = "";

        Log.v("SQL SENTENCE", sql);

        db.execSQL(sql);

    }

    public void deleteUser(int uid) {
        String sql = "DELETE FROM users WHERE _id =" + uid;

        Log.v("SQL SENTENCE", sql);

        db.execSQL(sql);
    }

    public long newGame(int uid, String startCoins) {

        ContentValues cv = new ContentValues();
        cv.put("uid", uid);
        cv.put("startCoins", startCoins);
        cv.put("finishCoins", "0");
        cv.put("startTime", new Date().toString());
        return  db.insert("games", null, cv);

    }

    public void finishGame(int uid, long idGame,String finishCoins) {

        String sql = "UPDATE games SET finishCoins='"+finishCoins+"', finishTime='"+ new Date().toString() +"' WHERE uid="+uid+" AND _id = " + idGame;

        Log.v("SQL SENTENCE", sql);

        db.execSQL(sql);

    }

    public void deleteGames(int uid) {

        String sql = "DELETE FROM games WHERE uid="+uid;

        Log.v("SQL SENTENCE", sql);

        db.execSQL(sql);

    }
    @SuppressLint("Range")
    public ArrayList<Game> getGames(int uid) {

        String sql = "SELECT * FROM games WHERE uid="+uid;

        Log.v("SQL SENTENCE", sql);

        ArrayList<Game> games = new ArrayList<>();

        Cursor c = db.rawQuery(sql, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    int id = c.getInt(c.getColumnIndex("_id"));
                    String startCoins = c.getString(c.getColumnIndex("startCoins"));
                    String finishCoins = c.getString(c.getColumnIndex("finishCoins"));
                    String startTime = c.getString(c.getColumnIndex("startTime"));
                    String finishTime = c.getString(c.getColumnIndex("finishTime"));
                    games.add(new Game(id, new BigInteger(startCoins), new BigInteger(finishCoins), startTime, finishTime));

                } while (c.moveToNext());
            }
        }
        c.close();

        return games;

    }

    private static String sha256(final String base) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

}
