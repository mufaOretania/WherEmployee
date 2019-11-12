package com.example.wheremployee.Entidades;

import java.util.ArrayList;

public class Empleado {

    private Integer id;
    private String nombreEmpleado;
    private String dniEmpleado;
    private String telefono;
    private String direccion;
    private String usuarioEmpleado;
    private String contrasena;

    private ArrayList<Jornada> jornadas;

    public Empleado(Integer id, String nombreEmpleado, String dniEmpleado, String telefono, String direccion, String usuarioEmpleado, String contrasena, ArrayList<Jornada> jornadas) {
        this.id = id;
        this.nombreEmpleado = nombreEmpleado;
        this.dniEmpleado = dniEmpleado;
        this.telefono = telefono;
        this.direccion = direccion;
        this.usuarioEmpleado = usuarioEmpleado;
        this.contrasena = contrasena;
        this.jornadas = jornadas;
    }

    public ArrayList<Jornada> getJornadas() {
        return jornadas;
    }

    public void setJornadas(ArrayList<Jornada> jornadas) {
        this.jornadas = jornadas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getDniEmpleado() {
        return dniEmpleado;
    }

    public void setDniEmpleado(String dniEmpleado) {
        this.dniEmpleado = dniEmpleado;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUsuarioEmpleado() {
        return usuarioEmpleado;
    }

    public void setUsuarioEmpleado(String usuarioEmpleado) {
        this.usuarioEmpleado = usuarioEmpleado;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
