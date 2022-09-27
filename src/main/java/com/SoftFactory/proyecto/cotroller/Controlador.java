package com.SoftFactory.proyecto.cotroller;

import com.SoftFactory.proyecto.entidades.Empresa;
import com.SoftFactory.proyecto.servicios.EmpresaServi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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

    @GetMapping("/EditarEmpresa/{id}")
    public String editarEmpresa(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje){
        Empresa empr=empresaServi.getEmpresaById(id);
        //Creamos un atributo para el modelo, que se llame igualmente emp y es el que ira al html para llenar o alimentar campos
        model.addAttribute("empr", empr);
        model.addAttribute("mensaje", mensaje);
        return "editarEmpresa";
    }


    @PostMapping("/ActualizarEmpresa")
    public String updateEmpresa(@ModelAttribute("empr") Empresa empr, RedirectAttributes redirectAttributes){
        if(empresaServi.saveOrUpdateEmpresa(empr)){
            redirectAttributes.addFlashAttribute("mensaje","updateOK");
            return "redirect:/VerListaEmpresas";
        }
        redirectAttributes.addFlashAttribute("mensaje","updateError");
        return "redirect:/EditarEmpresa/"+empr.getId();

    }

    @GetMapping("/EliminarEmpresa/{id}")
    public String eliminarEmpresa(@PathVariable Integer id){
        try {
            empresaServi.deleEmpresa(id);
        }catch(Exception e){
                return "redirect:/VerListaEmpresas";
            }
                return "redirect:/VerListaEmpresas";

        }
}
