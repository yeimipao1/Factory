package com.SoftFactory.proyecto.servicios;


import com.SoftFactory.proyecto.entidades.Empleado;
import com.SoftFactory.proyecto.repositorios.EmpleadoRepo;
import com.SoftFactory.proyecto.repositorios.EmpresaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpleadoServi {

    @Autowired
    EmpleadoRepo empleadoRepo;

    //Metodo para ver todos los empleados

    public List<Empleado> getAllEmpleados(){
        List<Empleado> empleadoList = new ArrayList<>();
        empleadoRepo.findAll().forEach(empleado -> empleadoList.add(empleado));
        return empleadoList;
    }


    //Metodo para ver al empleado por el id
    public Empleado getEmpleadoById(Integer id){
        return empleadoRepo.findById(id).get();
    }

    //Metodo para buscar empleados por ID
    /*public Optional<Empleado> getEmpleadoById(Integer id){ //Existe optional y asi se podria usar

        return empleadoRepo.findById(id);
    }*/


    public boolean saveOrUpdateEmpleado(Empleado empleado){
        Empleado empl=empleadoRepo.save(empleado);
        if (empleadoRepo.findById(empl.getId())!=null){
            return true;
        }
        return false;
    }
    /*public Empleado saveOrUpdateEmpleado(Empleado empleado){//es para el @RestController
        Empleado empl=empleadoRepo.save(empleado);
        return empl;
    }*/

    //Metod para eliminar un empleado por el ID

    public boolean deleteEmpleado(Integer id){
        empleadoRepo.deleteById(id);
        if (empleadoRepo.findById(id)!=null){
            return true;
        }
        return false;
    }

    //Método para ver los empleados que tiene cierta empresa
    public ArrayList<Empleado> empleadosByEmpresa(Integer id){
        return empleadoRepo.findByEmpresa(id);
    }

}
