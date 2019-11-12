package com.example.wheremployee.Entidades;

import java.util.ArrayList;
import java.util.TimeZone;

public class Jornada {

    private Integer id;
    private TimeZone horaInicio;
    private TimeZone horaFin;
    private ArrayList<Coordenada> cordenadas;

    public Jornada(Integer id, TimeZone horaInicio, TimeZone horaFin, ArrayList<Coordenada> cordenadas) {
        this.id = id;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.cordenadas = cordenadas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TimeZone getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(TimeZone horaInicio) {
        this.horaInicio = horaInicio;
    }

    public TimeZone getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(TimeZone horaFin) {
        this.horaFin = horaFin;
    }

    public ArrayList<Coordenada> getCordenadas() {
        return cordenadas;
    }

    public void setCordenadas(ArrayList<Coordenada> cordenadas) {
        this.cordenadas = cordenadas;
    }
}
