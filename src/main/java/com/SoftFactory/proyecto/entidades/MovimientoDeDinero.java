package com.SoftFactory.proyecto.entidades;


import javax.persistence.*;

@Entity
@Table(name = "MovimientoDeDinero")
public class MovimientoDeDinero {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = " id ", nullable = false)
    private int id;
    private long montoDelMovimiento;
    private String conceptoMovimiento;
    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado usuario;

    public MovimientoDeDinero() {
    }

    public MovimientoDeDinero(long montoDelMovimiento, String conceptoMovimiento, Empleado usuario) {
        this.montoDelMovimiento = montoDelMovimiento;
        this.conceptoMovimiento = conceptoMovimiento;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getMontoDelMovimiento() {
        return montoDelMovimiento;
    }

    public void setMontoDelMovimiento(long montoDelMovimiento) {
        this.montoDelMovimiento = montoDelMovimiento;
    }

    public String getConceptoMovimiento() {
        return conceptoMovimiento;
    }

    public void setConceptoMovimiento(String conceptoMovimiento) {
        this.conceptoMovimiento = conceptoMovimiento;
    }

    public Empleado getUsuario() {
        return usuario;
    }

    public void setUsuario(Empleado usuario) {
        this.usuario = usuario;
    }
}