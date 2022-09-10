package com.SoftFactory.proyecto.repositorios;

import com.SoftFactory.proyecto.entidades.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository//esta clase es un repositorio
public interface EmpresaRepo extends JpaRepository <Empresa, Integer> {
}
