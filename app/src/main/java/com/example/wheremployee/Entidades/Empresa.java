package com.example.wheremployee.Entidades;

public class Empresa {

    private Integer idEmp;
    private String nombreEmp;
    private String nombrePropietario;
    private String dniPropietario;
    private String telefono;
    private String direccion;
    private String usuarioJefe;
    private String contrasena;

    public Empresa(Integer idEmp, String nombreEmp, String nombrePropietario, String dniPropietario, String telefono, String direccion, String usuarioJefe, String contrasena) {
        this.idEmp = idEmp;
        this.nombreEmp = nombreEmp;
        this.nombrePropietario = nombrePropietario;
        this.dniPropietario = dniPropietario;
        this.telefono = telefono;
        this.direccion = direccion;
        this.usuarioJefe = usuarioJefe;
        this.contrasena = contrasena;
    }

    public Integer getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(Integer idEmp) {
        this.idEmp = idEmp;
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
