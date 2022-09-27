package com.SoftFactory.proyecto.entidades;


import javax.persistence.*;

@Entity
@Table(name = "MovimientoDeDinero")
public class MovimientoDeDinero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private long montoDelMovimiento;
    private String conceptoMovimiento;
    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado usuario;
    @DateTimeFormat(pattern = "yyy-MM-dd")
    private Date fecha;

    public MovimientoDeDinero() {
    }

    public MovimientoDeDinero(long montoDelMovimiento, String conceptoMovimiento, Empleado empleado, Date fecha) {
        this.montoDelMovimiento = montoDelMovimiento;
        this.conceptoMovimiento = conceptoMovimiento;
        this.usuario = empleado;
        this.fecha= fecha;
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

    public void setUsuario(Empleado empleado) {

        this.usuario = empleado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
