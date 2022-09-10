package com.SoftFactory.proyecto.cotroller;

import com.SoftFactory.proyecto.entidades.Empresa;
import com.SoftFactory.proyecto.servicios.EmpresaServi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class Controlador {
    @Autowired
    EmpresaServi empresaServi;

    @GetMapping ({"/","/VerListaEmpresas"})
    public String viewEmpresas(Model model){
        List<Empresa> listaEmpresas=empresaServi.getAllEmpresa();
        model.addAttribute("factlist",listaEmpresas);
        return "verListaEmpresas"; //llamamos al HTML
    }
}
