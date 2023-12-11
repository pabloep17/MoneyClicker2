package com.example.contador.Util;

import android.util.Log;

import com.example.contador.Info;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

public class Formatter {

    public String formarNumber(BigInteger number) {

        String result = "";

        if (number.compareTo(BigInteger.valueOf(1000)) < 0) {
            return String.format("%s%s", number, "");
        }

        int exp = (int) (Math.log(number.doubleValue()) / Math.log(1000));

        ArrayList<String>  valoresNumericos = new ArrayList<>();

        valoresNumericos.add("Miles");
        valoresNumericos.add("Millones");
        valoresNumericos.add("Billones");
        valoresNumericos.add("Trillones");
        valoresNumericos.add("Cuatrillones");
        valoresNumericos.add("Quintillones");
        valoresNumericos.add("Sextillones");
        valoresNumericos.add("Septillones");

        try {
            Log.v("NumberFormater", valoresNumericos.get(exp-1));
            result = String.format("%.2f\n%s", number.doubleValue() / Math.pow(1000, exp),  valoresNumericos.get(exp-1));
        } catch (IndexOutOfBoundsException e) {
            result = "MAX";
        }

        return result;

    }

    public String formartDate(String fecha) {

        String dias[] = {"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio","Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

        String result = "";

        Date nuevaFecha = new Date(fecha);

        result += dias[nuevaFecha.getDay()];
        result += " " + nuevaFecha.getDate();
        result += " de " + meses[nuevaFecha.getMonth()];
        result += " de " + (Integer.valueOf(nuevaFecha.getYear()) + 1900);

        result += " " + ((nuevaFecha.getHours() + 2) + ":" + (nuevaFecha.getMinutes() < 10 ? "0" + nuevaFecha.getMinutes() : nuevaFecha.getMinutes()));

        return  result;
    }

}
