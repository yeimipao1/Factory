package com.SoftFactory.proyecto.entidades;

import javax.persistence.*;

@Entity
@Table(name="Empresa")

public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombreEmpresa;

    private String direccion;

    private int telefono;

    private String NIT;

    public Empresa() {
    }

    public Empresa(String nombreEmpresa, String direccion, int telefono, String NIT) {
        this.nombreEmpresa = nombreEmpresa;
        this.direccion = direccion;
        this.telefono = telefono;
        this.NIT = NIT;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getNombreEmpresa() {

        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {

        this.nombreEmpresa = nombreEmpresa;
    }

    public String getDireccion() {

        return direccion;
    }

    public void setDireccion(String direccion) {

        this.direccion = direccion;
    }

    public int getTelefono() {

        return telefono;
    }

    public void setTelefono(int telefono) {

        this.telefono = telefono;
    }

    public String getNIT() {

        return NIT;
    }

    public void setNIT(String NIT) {

        this.NIT = NIT;
    }
}
