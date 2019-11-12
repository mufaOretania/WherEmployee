package com.example.wheremployee.Entidades;

import java.util.ArrayList;

public class Empresa {

    private Integer id;
    private String nombreEmp;
    private String nombrePropietario;
    private String dniPropietario;
    private String telefono;
    private String direccion;
    private String usuarioJefe;
    private String contrasena;
    private ArrayList<Empleado> empleados;

    public Empresa(Integer id, String nombreEmp, String nombrePropietario, String dniPropietario, String telefono, String direccion, String usuarioJefe, String contrasena, ArrayList<Empleado> empleados) {
        this.id = id;
        this.nombreEmp = nombreEmp;
        this.nombrePropietario = nombrePropietario;
        this.dniPropietario = dniPropietario;
        this.telefono = telefono;
        this.direccion = direccion;
        this.usuarioJefe = usuarioJefe;
        this.contrasena = contrasena;
        this.empleados = empleados;
    }

    public Integer getIdJefe() {
        return id;
    }

    public void setIdJefe(Integer idJefe) {
        this.id = idJefe;
    }

    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(ArrayList<Empleado> empleados) {
        this.empleados = empleados;
    }

    public String getNombreEmp() {
        return nombreEmp;
    }

    public void setNombreEmp(String nombreEmp) {
        this.nombreEmp = nombreEmp;
    }

    public String getNombrePropietario() {
        return nombrePropietario;
    }

    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
    }

    public String getDniPropietario() {
        return dniPropietario;
    }

    public void setDniPropietario(String dniPropietario) {
        this.dniPropietario = dniPropietario;
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

    public String getUsuarioJefe() {
        return usuarioJefe;
    }

    public void setUsuarioJefe(String usuarioJefe) {
        this.usuarioJefe = usuarioJefe;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
