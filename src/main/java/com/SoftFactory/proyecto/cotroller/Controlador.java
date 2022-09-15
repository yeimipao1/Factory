package com.SoftFactory.proyecto.cotroller;

import com.SoftFactory.proyecto.entidades.Empresa;
import com.SoftFactory.proyecto.servicios.EmpresaServi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @GetMapping("/AgregarEmpresas")
    public String newEmpresa(Model model){
        Empresa empr= new Empresa();
        model.addAttribute("empr", empr);
        return "agregarEmpresa";
    }

    @PostMapping("/GuardarEmpresa")
    public String guardarEmpresa(Empresa empr, RedirectAttributes redirectAttributes){
        if(empresaServi.saveOrUpdateEmpresa(empr)==true){
            redirectAttributes.addFlashAttribute("mensaje","saveOK");
            return "redirect:/VerListaEmpresas";
        }

        return "redirect:/AgregarEmpresas";
    }
}
