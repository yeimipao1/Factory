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
    @Autowired
    EmpresaRepo empresaRepo;

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

    //Metodo para buscar empleados por empresa
    public ArrayList<Empleado> obtenerPorEmpresa(Integer id){
        return empleadoRepo.findByEmpresa(id);
    }

    //Metodo para guardar o actualizar registros en Empleados
    public boolean saveOrUpdateEmpleado(Empleado empl){
        Empleado emp=empleadoRepo.save(empl);
        if (empleadoRepo.findById(emp.getId())!=null){
            return true;
        }
        return false;
    }

    //Metod para eliminar un empleado por el ID

    public boolean deleteEmpleado(Integer id){
        empleadoRepo.deleteById(id);
        if (empleadoRepo.findById(id)!=null){
            return true;
        }
        return false;
    }

    //Método para ver los empleados que tiene cierta empresa
    public ArrayList<Empleado> empleadosByEmpresa (Integer id){
        return empleadoRepo.findByEmpresa(id);
    }

}
