/*package com.SoftFactory.proyecto.Entidades;


import javax.persistence.*;

@Entity
@Table(name = "MovimientoDeDinero")
public class MovimientoDeDinero {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = " id ", nullable = false)

    //ATRIBUTOS
    private int id;

    private double montoDelMovimiento;

    private String conceptoMovimiento;
    // @ManyToOne
    // @JoinColumn ( name = " empleado_id " )
    // private  Empleado usuario;

    public MovimientoDeDinero() {
    }

    public MovimientoDeDinero(double montoDelMovimiento, String conceptoMovimiento) {
        this.montoDelMovimiento = montoDelMovimiento;
        this.conceptoMovimiento = conceptoMovimiento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMontoDelMovimiento() {
        return montoDelMovimiento;
    }

    public void setMontoDelMovimiento(double montoDelMovimiento) {
        this.montoDelMovimiento = montoDelMovimiento;
    }

    public String getConceptoMovimiento() {
        return conceptoMovimiento;
    }

    public void setConceptoMovimiento(String conceptoMovimiento) {
        this.conceptoMovimiento = conceptoMovimiento;
    }
}*/