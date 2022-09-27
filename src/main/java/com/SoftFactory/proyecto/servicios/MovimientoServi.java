package com.SoftFactory.proyecto.servicios;

import com.SoftFactory.proyecto.entidades.MovimientoDeDinero;
import com.SoftFactory.proyecto.repositorios.MovimientosRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovimientoServi {
    @Autowired
    MovimientosRepo movimientoDeDineroRepo;

    public List<MovimientoDeDinero> getAllMovimientos() { //Metodo que me muestra todos los movimientos sin ningn filtro
        List<MovimientoDeDinero> movimientosList = new ArrayList<>();
        movimientoDeDineroRepo.findAll().forEach(movimiento -> movimientosList.add(movimiento));  //Recorremos el iterable que regresa el metodo findAll del Jpa y lo guardamos en la lista creada
        return movimientosList;
    }

    public MovimientoDeDinero getMovimientoById(Integer id) { //Ver movimientos por id

        return movimientoDeDineroRepo.findById(id).get();
    }

    public boolean saveOrUpdateMovimiento(MovimientoDeDinero movimientoDinero) { //Guardar o actualizar elementos
        MovimientoDeDinero mov = movimientoDeDineroRepo.save(movimientoDinero);
        if (movimientoDeDineroRepo.findById(mov.getId()) != null) {
            return true;
        }
        return false;
    }

    public boolean deleteMovimiento(Integer id) { //Eliminar movimiento por id
        movimientoDeDineroRepo.deleteById(id); //Eliminar usando el metodo que nos ofrece el repositorio
        if (this.movimientoDeDineroRepo.findById(id).isPresent()) { //Si al buscar el movimiento lo encontramos, no se eliminó (false)
            return false;
        }
        return true; //Si al buscar el movimiento no lo encontramos, si lo eliminò (true)
    }

    public ArrayList<MovimientoDeDinero> obtenerPorEmpleado(Integer id) { //Obterner teniendo en cuenta el id del empleado
        return movimientoDeDineroRepo.findByEmpleado(id);
    }

    public ArrayList<MovimientoDeDinero> obtenerPorEmpresa(Integer id) { //Obtener movimientos teniendo en cuenta el id de la empresa a la que pertencen los empleados que la registraron
        return movimientoDeDineroRepo.findByEmpresa(id);
    }

    //Servicio para ver la suma de todos los montos
    public Long obtenerSumaMontos() {
        return movimientoDeDineroRepo.SumarMonto();
    }

    //Servicio para ver la suma de los montos por empleado
    public Long MontosPorEmpleado(Integer id) {
        return movimientoDeDineroRepo.MontosPorEmpleado(id);
    }

    //Servicio para ver la suma de los montos por empresa
    public Long MontosPorEmpresa(Integer id) {
        return movimientoDeDineroRepo.MontosPorEmpresa(id);
    }

    //servicio que nos deja conseguir el id de un empleado si tenemos su correo
    public Integer IdPorCorreo(String Correo) {
        return movimientoDeDineroRepo.IdPorCorreo(Correo);
    }
}
