package com.SoftFactory.proyecto.servicios;

import com.SoftFactory.proyecto.entidades.Empresa;
import com.SoftFactory.proyecto.repositorios.EmpresaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpresaServi {
    @Autowired// conectamos esta clase con el repositorio de empresa
    EmpresaRepo empresaRepo;
    //metodo que retorná la lista deempresas usando metodos heredados del jpaRepository
    public List<Empresa> getAllEmpresa(){
        List<Empresa>empresaList = new ArrayList<>();
        empresaRepo.findAll().forEach(empresa -> empresaList.add(empresa));//recorremos la
        return empresaList;
    }
    //metodo que trae un objetode tipo emprescuando cuento con el id de la misma
    public Empresa getEmpresaById(Integer id){
        return empresaRepo.findById(id).get();
    }

    //Metodo para guardar o actualizar objetos de tipo Empresa
    public boolean saveOrUpdateEmpresa(Empresa empresa){
        Empresa fact=empresaRepo.save(empresa);
        if (empresaRepo.findById((int) fact.getId())!=null){
           return true;
        }
        return false;
    }
    //Metodo para eliminar empresa registradas teniendo el id
    public boolean deleEmpresa(Integer id){
        empresaRepo.deleteById(id);
        if (getEmpresaById(id)!=null){
            return false;
        }
        return true;
    }
}
