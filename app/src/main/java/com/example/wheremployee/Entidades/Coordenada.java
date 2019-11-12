package com.example.wheremployee.Entidades;

public class Coordenada {

    private Integer id;
    private Float latitud;
    private Float longitud;

    public Coordenada(Integer id, Float latitud, Float longitud) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }
}
